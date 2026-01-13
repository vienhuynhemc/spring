/* vienhuynhemc */
package com.vienhuynhemc.outbox_pattern.service;

import com.vienhuynhemc.outbox_pattern.entity.OutboxEvent;
import com.vienhuynhemc.outbox_pattern.model.OrderStatusUpdatedEvent;
import jakarta.annotation.Nonnull;
import org.apache.kafka.clients.producer.ProducerRecord;

public interface OrderStatusUpdatedService {
  void processOrder();

  @Nonnull
  ProducerRecord<String, OrderStatusUpdatedEvent> createRecord(@Nonnull OutboxEvent event);
}
