/* vienhuynhemc */
package com.vienhuynhemc.outbox_pattern.cron_job;

import com.vienhuynhemc.outbox_pattern.entity.OutboxEvent;
import com.vienhuynhemc.outbox_pattern.model.OrderStatusUpdatedEvent;
import com.vienhuynhemc.outbox_pattern.service.OrderStatusUpdatedService;
import com.vienhuynhemc.outbox_pattern.service.OutboxEventService;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxEventCronJob {

  private final KafkaTemplate<String, OrderStatusUpdatedEvent> kafkaTemplate;
  private final OutboxEventService outboxEventService;
  private final OrderStatusUpdatedService orderStatusUpdatedService;

  @Scheduled(fixedRate = 1, initialDelay = 1, timeUnit = TimeUnit.SECONDS)
  public void outboxEventCronJobCronJob() {
    final List<OutboxEvent> outboxEvents = outboxEventService.getValidPendingOutboxEvents(
      "order.status.v1",
      Instant.now()
    );

    for (OutboxEvent event : outboxEvents) {
      final ProducerRecord<String, OrderStatusUpdatedEvent> record = orderStatusUpdatedService.createRecord(event);
      kafkaTemplate
        .send(record)
        .whenComplete((_, exception) -> {
          if (exception == null) {
            outboxEventService.markSent(event);
          } else {
            outboxEventService.markFailed(event, exception);
          }
        });
    }

    outboxEventService.saveOutboxEvents(outboxEvents);
  }
}
