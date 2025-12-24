/* vienhuynhemc */
package com.vienhuynhemc.resume_logic.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.InvalidJobParametersException;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.JobRestartException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultBatchJobService implements BatchJobService {

  private final JobOperator jobOperator;
  private final Job job;

  @Override
  public void triggerEmailBatchJob() {
    try {
      final LocalDateTime param = LocalDateTime.of(2025, 10, 10, 10, 10, 10);

      final JobParameters jobParameters = new JobParametersBuilder()
        .addLocalDateTime("scheduledDate", param)
        .toJobParameters();
      jobOperator.start(job, jobParameters);
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
