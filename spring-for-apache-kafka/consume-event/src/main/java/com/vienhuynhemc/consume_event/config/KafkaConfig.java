/* vienhuynhemc */
package com.vienhuynhemc.consume_event.config;

import com.vienhuynhemc.consume_event.model.OrderStatusUpdatedEvent;
import jakarta.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@Configuration
public class KafkaConfig {

  @Bean
  public NewTopic orderStatusTopic() {
    return TopicBuilder.name("order.status.v1").partitions(5).build();
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<
    String,
    OrderStatusUpdatedEvent
  > orderStatusUpdatedKafkaListenerContainerFactory(@Nonnull KafkaProperties kafkaProperties) {
    final Map<String, Object> defaultConsumerProps = kafkaProperties.buildConsumerProperties();
    final Map<String, Object> copiedProps = new HashMap<>(defaultConsumerProps);

    final ConsumerFactory<String, OrderStatusUpdatedEvent> consumerFactory = new DefaultKafkaConsumerFactory<>(
      copiedProps
    );

    final ConcurrentKafkaListenerContainerFactory<String, OrderStatusUpdatedEvent> factory =
      new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory);

    return factory;
  }
}
