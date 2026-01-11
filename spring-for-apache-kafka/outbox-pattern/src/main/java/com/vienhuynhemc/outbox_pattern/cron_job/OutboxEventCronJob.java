/* vienhuynhemc */
package com.vienhuynhemc.outbox_pattern.cron_job;

import com.vienhuynhemc.outbox_pattern.model.OrderStatusUpdatedEvent;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxEventCronJob {

  private final KafkaTemplate<String, OrderStatusUpdatedEvent> kafkaTemplate;

  @Scheduled(fixedRate = 1, initialDelay = 1, timeUnit = TimeUnit.SECONDS)
  public void orderStatusUpdateCronCronJob() {
    //    final List<OrderStatusUpdatedEvent> events = generateOrderStatusUpdatedEvents();
    //
    //    for (OrderStatusUpdatedEvent event : events) {
    //      final ProducerRecord<String, OrderStatusUpdatedEvent> record = new ProducerRecord<>(
    //        "order.status.v1",
    //        null,
    //        Instant.now().toEpochMilli(),
    //        event.orderId().toString(),
    //        event
    //      );
    //      kafkaTemplate.send(record);
    //    }
  }
}
