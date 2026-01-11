/* vienhuynhemc */
package com.vienhuynhemc.outbox_pattern.cron_job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vienhuynhemc.outbox_pattern.entity.OutboxEvent;
import com.vienhuynhemc.outbox_pattern.model.OrderStatusUpdatedEvent;
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
  private final ObjectMapper objectMapper;

  @Scheduled(fixedRate = 1, initialDelay = 1, timeUnit = TimeUnit.SECONDS)
  public void outboxEventCronJobCronJob() throws JsonProcessingException {
    final List<OutboxEvent> outboxEvents = outboxEventService.getValidPendingOutboxEvents(
      "order.status.v1",
      Instant.now()
    );

    for (OutboxEvent event : outboxEvents) {
      final OrderStatusUpdatedEvent payload = objectMapper.treeToValue(
        event.getPayload(),
        OrderStatusUpdatedEvent.class
      );
      final ProducerRecord<String, OrderStatusUpdatedEvent> record = new ProducerRecord<>(
        event.getEventType(),
        null,
        Instant.now().toEpochMilli(),
        event.getAggregateId(),
        payload
      );

      kafkaTemplate.send(record);
    }
  }
}
