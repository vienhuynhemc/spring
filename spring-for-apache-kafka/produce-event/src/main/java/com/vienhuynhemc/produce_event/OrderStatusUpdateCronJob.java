/* vienhuynhemc */
package com.vienhuynhemc.produce_event;

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
        event.orderId(),
        event
      );
      kafkaTemplate.send(record);
    }
  }

  private List<OrderStatusUpdatedEvent> generateOrderStatusUpdatedEvents() {
    final List<OrderStatusUpdatedEvent> events = new ArrayList<>();
    final UUID orderId = UUID.randomUUID();

    final OrderStatusUpdatedEvent firstEvent = new OrderStatusUpdatedEvent(
      UUID.randomUUID().toString(),
      orderId.toString(),
      OrderStatus.DRAFT.name(),
      OrderStatus.PENDING.name(),
      Instant.now()
    );
    events.add(firstEvent);

    boolean isHaveComplete = random.nextBoolean();
    if (isHaveComplete) {
      final OrderStatusUpdatedEvent secondEvent = new OrderStatusUpdatedEvent(
        UUID.randomUUID().toString(),
        orderId.toString(),
        OrderStatus.PENDING.name(),
        OrderStatus.COMPLETED.name(),
        Instant.now()
      );
      events.add(secondEvent);
    }

    return events;
  }
}
