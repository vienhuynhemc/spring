/* vienhuynhemc */
package com.vienhuynhemc.resume_logic.batch_job;

import com.vienhuynhemc.resume_logic.entity.EmailOutbox;
import com.vienhuynhemc.resume_logic.model.ProcessStatus;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.retry.RetryPolicy;
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
    provider.setWhereClause("WHERE status = 'NEW' AND scheduled_date = :scheduledDate");

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
  @StepScope
  public JdbcPagingItemReader<EmailOutbox> reader(
    DataSource dataSource,
    PagingQueryProvider queryProvider,
    RowMapper<EmailOutbox> emailOutboxRowMapper,
    @Value("#{jobParameters['scheduledDate']}") LocalDateTime scheduledDate
  ) throws Exception {
    final Map<String, Object> params = new HashMap<>();
    params.put("scheduledDate", scheduledDate);

    return new JdbcPagingItemReaderBuilder<EmailOutbox>()
      .name("emailOutboxReader")
      .dataSource(dataSource)
      .queryProvider(queryProvider)
      .parameterValues(params)
      .rowMapper(emailOutboxRowMapper)
      .pageSize(CHUNK_SIZE * 10)
      .build();
  }

  @Bean
  public ItemProcessor<EmailOutbox, EmailOutbox> processor() {
    return item -> {
      item.setStatus(ProcessStatus.SUCCESS);
      return item;
    };
  }

  @Bean
  public ItemSqlParameterSourceProvider<EmailOutbox> itemSqlParameterSourceProvider() {
    return item ->
      new MapSqlParameterSource()
        .addValue("id", item.getId())
        .addValue("status", item.getStatus().name(), Types.VARCHAR);
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
  public RetryPolicy retryPolicy() {
    final int retryLimit = 3;
    final Set<Class<? extends Throwable>> retryableExceptions = Set.of(TimeoutException.class);

    return RetryPolicy.builder().maxRetries(retryLimit).includes(retryableExceptions).build();
  }

  @Bean
  public Step step(
    JobRepository jobRepository,
    ItemReader<EmailOutbox> reader,
    ItemProcessor<EmailOutbox, EmailOutbox> processor,
    ItemWriter<EmailOutbox> writer,
    PlatformTransactionManager transactionManager,
    RetryPolicy retryPolicy
  ) {
    return new StepBuilder(jobRepository)
      .<EmailOutbox, EmailOutbox>chunk(CHUNK_SIZE)
      .transactionManager(transactionManager)
      .reader(reader)
      .processor(processor)
      .writer(writer)
      .faultTolerant()
      .retryPolicy(retryPolicy)
      .build();
  }

  @Bean
  public Job job(JobRepository jobRepository, Step step, Step prepareEmailOutboxStep) {
    return new JobBuilder("emailOutbox", jobRepository).start(prepareEmailOutboxStep).next(step).build();
  }
}
