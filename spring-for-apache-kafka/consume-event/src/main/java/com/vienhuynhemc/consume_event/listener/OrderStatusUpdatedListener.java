/* vienhuynhemc */
package com.vienhuynhemc.consume_event.listener;

import com.vienhuynhemc.consume_event.model.OrderStatusUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderStatusUpdatedListener {

  @KafkaListener(topics = "order.status.v1", containerFactory = "orderStatusUpdatedKafkaListenerContainerFactory")
  public void listenOrderStatusUpdated(OrderStatusUpdatedEvent event) {
    log.info(event.toString());
  }
}
