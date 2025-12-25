/* vienhuynhemc */
package com.vienhuynhemc.partition_logic.batch_job;

import com.vienhuynhemc.partition_logic.entity.EmailOutbox;
import com.vienhuynhemc.partition_logic.model.ProcessStatus;
import com.vienhuynhemc.partition_logic.service.EmailOutboxService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class PrepareEmailOutboxBatchStepConfig {

  @Bean
  public Tasklet emailOutboxTasklet(EmailOutboxService emailOutboxService) {
    return (_, chunkContext) -> {
      final LocalDateTime scheduledDate = (LocalDateTime) chunkContext
        .getStepContext()
        .getJobParameters()
        .get("scheduledDate");

      final List<EmailOutbox> emailOutboxes = new ArrayList<>();
      for (int i = 0; i < 1000; i++) {
        final EmailOutbox emailOutbox = new EmailOutbox();
        emailOutbox.setId(UUID.randomUUID());
        emailOutbox.setStatus(ProcessStatus.NEW);
        emailOutbox.setCreatedAt(LocalDateTime.now());
        emailOutbox.setScheduledDate(scheduledDate);
        emailOutboxes.add(emailOutbox);
      }
      emailOutboxService.saveAll(emailOutboxes);

      return RepeatStatus.FINISHED;
    };
  }

  @Bean
  public Step prepareEmailOutboxStep(
    JobRepository jobRepository,
    PlatformTransactionManager transactionManager,
    Tasklet emailOutboxTasklet
  ) {
    return new StepBuilder(jobRepository).tasklet(emailOutboxTasklet).transactionManager(transactionManager).build();
  }
}
