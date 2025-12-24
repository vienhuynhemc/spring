/* vienhuynhemc */
package com.vienhuynhemc.single_datasource.service;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.InvalidJobParametersException;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.launch.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.JobRestartException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultEmailOutboxService implements EmailOutboxService {

  private final JobOperator jobOperator;
  private final Job job;

  @Override
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
