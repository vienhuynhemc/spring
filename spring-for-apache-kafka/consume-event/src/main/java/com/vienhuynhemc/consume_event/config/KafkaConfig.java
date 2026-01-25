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
import org.springframework.kafka.support.serializer.JsonDeserializer;

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
    final ConcurrentKafkaListenerContainerFactory<String, OrderStatusUpdatedEvent> factory =
      new ConcurrentKafkaListenerContainerFactory<>();

    final Map<String, Object> defaultProps = kafkaProperties.buildConsumerProperties();
    final Map<String, Object> props = new HashMap<>(defaultProps);
    props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.vienhuynhemc.consume_event.model");

    final ConsumerFactory<String, OrderStatusUpdatedEvent> consumerFactory = new DefaultKafkaConsumerFactory<>(props);

    factory.setConsumerFactory(consumerFactory);
    factory.setConcurrency(5);

    return factory;
  }
}
