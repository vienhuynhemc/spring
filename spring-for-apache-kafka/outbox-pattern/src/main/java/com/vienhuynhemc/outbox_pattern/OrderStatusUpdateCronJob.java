/* vienhuynhemc */
package com.vienhuynhemc.outbox_pattern;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStatusUpdateCronJob {

  private final KafkaTemplate<String, OrderStatusUpdatedEvent> kafkaTemplate;
  private final Random random = new Random();

  @Scheduled(fixedRate = 1, initialDelay = 1, timeUnit = TimeUnit.SECONDS)
  public void orderStatusUpdateCronCronJob() {
    final List<OrderStatusUpdatedEvent> events = generateOrderStatusUpdatedEvents();

    for (OrderStatusUpdatedEvent event : events) {
      final ProducerRecord<String, OrderStatusUpdatedEvent> record = new ProducerRecord<>(
        "order.status.v1",
        null,
        Instant.now().toEpochMilli(),
        event.orderId().toString(),
        event
      );
      kafkaTemplate.send(record);
    }
  }

  private List<OrderStatusUpdatedEvent> generateOrderStatusUpdatedEvents() {
    final List<OrderStatusUpdatedEvent> events = new ArrayList<>();
    final UUID orderId = UUID.randomUUID();

    events.add(
      new OrderStatusUpdatedEvent(UUID.randomUUID(), orderId, OrderStatus.DRAFT, OrderStatus.PENDING, Instant.now())
    );

    boolean isHaveSubmitted = random.nextBoolean();
    if (isHaveSubmitted) {
      final UUID eventId = UUID.randomUUID();
      events.add(
        new OrderStatusUpdatedEvent(eventId, orderId, OrderStatus.PENDING, OrderStatus.SUBMITTED, Instant.now())
      );
    }

    boolean isHaveComplete = random.nextBoolean();
    if (isHaveComplete) {
      final UUID eventId = UUID.randomUUID();
      events.add(
        new OrderStatusUpdatedEvent(eventId, orderId, OrderStatus.SUBMITTED, OrderStatus.COMPLETED, Instant.now())
      );
    }

    return events;
  }
}
