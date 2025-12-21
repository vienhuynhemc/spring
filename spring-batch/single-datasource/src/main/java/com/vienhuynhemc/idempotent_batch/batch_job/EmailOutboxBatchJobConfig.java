/* vienhuynhemc */
package com.vienhuynhemc.idempotent_batch.batch_job;

import com.vienhuynhemc.idempotent_batch.entity.EmailOutbox;
import com.vienhuynhemc.idempotent_batch.model.ProcessStatus;
import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.database.ItemSqlParameterSourceProvider;
import org.springframework.batch.infrastructure.item.database.JdbcPagingItemReader;
import org.springframework.batch.infrastructure.item.database.Order;
import org.springframework.batch.infrastructure.item.database.PagingQueryProvider;
import org.springframework.batch.infrastructure.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.infrastructure.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.infrastructure.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class EmailOutboxBatchJobConfig {

  private static final int CHUNK_SIZE = 2;

  @Bean
  public SqlPagingQueryProviderFactoryBean queryProvider(DataSource dataSource) {
    final SqlPagingQueryProviderFactoryBean provider = new SqlPagingQueryProviderFactoryBean();

    provider.setDataSource(dataSource);

    provider.setSelectClause("SELECT id, status, created_at");
    provider.setFromClause("FROM email_outbox");
    provider.setWhereClause("WHERE status = 'NEW'");

    final Map<String, Order> sortKeys = new LinkedHashMap<>();
    sortKeys.put("created_at", Order.ASCENDING);
    sortKeys.put("id", Order.ASCENDING);
    provider.setSortKeys(sortKeys);

    return provider;
  }

  @Bean
  public RowMapper<EmailOutbox> emailOutboxRowMapper() {
    return (rs, _) -> {
      final EmailOutbox emailOutbox = new EmailOutbox();

      emailOutbox.setId(rs.getObject("id", UUID.class));
      emailOutbox.setStatus(ProcessStatus.valueOf(rs.getString("status")));

      return emailOutbox;
    };
  }

  @Bean
  public JdbcPagingItemReader<EmailOutbox> reader(
    DataSource dataSource,
    PagingQueryProvider queryProvider,
    RowMapper<EmailOutbox> emailOutboxRowMapper
  ) throws Exception {
    return new JdbcPagingItemReaderBuilder<EmailOutbox>()
      .name("emailOutboxReader")
      .dataSource(dataSource)
      .queryProvider(queryProvider)
      .rowMapper(emailOutboxRowMapper)
      .pageSize(CHUNK_SIZE * 10)
      .build();
  }

  @Bean
  public ItemProcessor<EmailOutbox, EmailOutbox> processor() {
    return item -> {
      Thread.sleep(1000);

      log.info("Processing email outbox {}", item.getId());
      item.setStatus(ProcessStatus.SUCCESS);

      return item;
    };
  }

  @Bean
  public ItemSqlParameterSourceProvider<EmailOutbox> itemSqlParameterSourceProvider() {
    return item -> {
      final MapSqlParameterSource params = new MapSqlParameterSource();

      params.addValue("id", item.getId());
      params.addValue("status", item.getStatus().name(), Types.VARCHAR);

      return params;
    };
  }

  @Bean
  public ItemWriter<EmailOutbox> writer(
    DataSource dataSource,
    ItemSqlParameterSourceProvider<EmailOutbox> itemSqlParameterSourceProvider
  ) {
    final String sql =
      """
      UPDATE email_outbox
      SET
        status = :status,
        updated_at = NOW()
      WHERE id = :id
      """;
    return new JdbcBatchItemWriterBuilder<EmailOutbox>()
      .dataSource(dataSource)
      .itemSqlParameterSourceProvider(itemSqlParameterSourceProvider)
      .sql(sql)
      .build();
  }

  @Bean
  public Step step(
    JobRepository jobRepository,
    ItemReader<EmailOutbox> reader,
    ItemProcessor<EmailOutbox, EmailOutbox> processor,
    ItemWriter<EmailOutbox> writer,
    PlatformTransactionManager transactionManager
  ) {
    return new StepBuilder(jobRepository)
      .<EmailOutbox, EmailOutbox>chunk(CHUNK_SIZE)
      .transactionManager(transactionManager)
      .reader(reader)
      .processor(processor)
      .writer(writer)
      .build();
  }

  @Bean
  public Job job(JobRepository jobRepository, Step step) {
    return new JobBuilder("emailOutbox", jobRepository).start(step).incrementer(new RunIdIncrementer()).build();
  }
}
