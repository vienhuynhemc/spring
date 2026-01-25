/* vienhuynhemc */
package com.vienhuynhemc.ordering_sender.cron_job;

import com.vienhuynhemc.ordering_sender.service.OrderStatusUpdatedService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStatusUpdateCronJob {

  private final OrderStatusUpdatedService orderStatusUpdatedService;

  @Scheduled(fixedRate = 1, initialDelay = 1, timeUnit = TimeUnit.SECONDS)
  public void orderStatusUpdateCronJob() {
    orderStatusUpdatedService.processOrder();
  }
}
