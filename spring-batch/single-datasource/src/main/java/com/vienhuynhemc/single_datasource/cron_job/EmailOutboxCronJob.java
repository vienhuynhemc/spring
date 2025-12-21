/* vienhuynhemc */
package com.vienhuynhemc.single_datasource.cron_job;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.InvalidJobParametersException;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.launch.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailOutboxCronJob {

  private final JobOperator jobOperator;
  private final Job job;

  @Scheduled(fixedRate = 10, initialDelay = 10, timeUnit = TimeUnit.SECONDS)
  public void emailOutboxCronJob() {
    log.info("Starting Email Outbox Cron Job");
    triggerEmailBatchJob();
    log.info("Finished Email Outbox Cron Job");
  }

  public void triggerEmailBatchJob() {
    try {
      jobOperator.start(job, new JobParameters());
    } catch (
      JobInstanceAlreadyCompleteException
      | JobExecutionAlreadyRunningException
      | InvalidJobParametersException
      | JobRestartException e
    ) {
      throw new RuntimeException(e);
    }
  }
}
