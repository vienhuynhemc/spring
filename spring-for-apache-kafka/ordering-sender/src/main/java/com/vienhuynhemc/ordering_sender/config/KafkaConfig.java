/* vienhuynhemc */
package com.vienhuynhemc.ordering_sender.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

  @Bean
  public NewTopic orderStatusTopic() {
    return TopicBuilder.name("order.status.v1").partitions(5).build();
  }
}
