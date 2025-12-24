/* vienhuynhemc */
package com.vienhuynhemc.resume_logic.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
      final LocalTime now = LocalTime.now();
      final LocalDateTime param = LocalDateTime.of(
        LocalDate.now(),
        LocalTime.of(now.getHour(), now.getMinute(), now.getSecond())
      );

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
