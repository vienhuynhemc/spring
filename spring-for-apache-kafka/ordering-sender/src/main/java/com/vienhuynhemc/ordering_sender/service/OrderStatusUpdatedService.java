/* vienhuynhemc */
package com.vienhuynhemc.ordering_sender.service;

import com.vienhuynhemc.ordering_sender.entity.OutboxEvent;
import com.vienhuynhemc.ordering_sender.model.OrderStatusUpdatedEvent;
import jakarta.annotation.Nonnull;
import org.apache.kafka.clients.producer.ProducerRecord;

public interface OrderStatusUpdatedService {
  void processOrder();

  @Nonnull
  ProducerRecord<String, OrderStatusUpdatedEvent> createRecord(@Nonnull OutboxEvent event);
}
