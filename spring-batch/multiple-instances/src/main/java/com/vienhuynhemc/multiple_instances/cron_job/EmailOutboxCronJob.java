/* vienhuynhemc */
package com.vienhuynhemc.multiple_instances.cron_job;

import com.vienhuynhemc.multiple_instances.service.BatchJobService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailOutboxCronJob {

  private final BatchJobService batchJobService;

  @Scheduled(fixedRate = 10, initialDelay = 10, timeUnit = TimeUnit.SECONDS)
  public void emailOutboxCronJob() {
    log.info("Starting Email Outbox Cron Job");
    batchJobService.triggerEmailBatchJob();
    log.info("Finished Email Outbox Cron Job");
  }
}
