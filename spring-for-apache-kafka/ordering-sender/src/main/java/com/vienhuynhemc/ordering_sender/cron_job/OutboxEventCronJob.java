/* vienhuynhemc */
package com.vienhuynhemc.ordering_sender.cron_job;

import com.vienhuynhemc.ordering_sender.entity.OutboxEvent;
import com.vienhuynhemc.ordering_sender.model.OrderStatusUpdatedEvent;
import com.vienhuynhemc.ordering_sender.service.OrderStatusUpdatedService;
import com.vienhuynhemc.ordering_sender.service.OutboxEventService;
import jakarta.annotation.Nonnull;
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
  public void outboxEventCronJob() {
    final List<OutboxEvent> outboxEvents = outboxEventService.getValidPendingOutboxEvents(
      "order.status.v1",
      Instant.now()
    );

    outboxEvents.forEach(this::sendEvent);
  }

  @Scheduled(fixedRate = 1, initialDelay = 1, timeUnit = TimeUnit.SECONDS)
  public void perKeyOutboxEventCronJob() {
    final List<OutboxEvent> outboxEvents = outboxEventService.getValidPendingPerKeyOutboxEvents(
      "order.status.v1",
      Instant.now()
    );

    outboxEvents.forEach(this::sendEvent);
  }

  private void sendEvent(@Nonnull OutboxEvent event) {
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
}
