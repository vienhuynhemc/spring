/* vienhuynhemc */
package com.vienhuynhemc.consume_event.service;

import com.vienhuynhemc.consume_event.entity.OutboxEvent;
import com.vienhuynhemc.consume_event.model.OrderStatusUpdatedEvent;
import jakarta.annotation.Nonnull;
import org.apache.kafka.clients.producer.ProducerRecord;

public interface OrderStatusUpdatedService {
  void processOrder();

  @Nonnull
  ProducerRecord<String, OrderStatusUpdatedEvent> createRecord(@Nonnull OutboxEvent event);
}
