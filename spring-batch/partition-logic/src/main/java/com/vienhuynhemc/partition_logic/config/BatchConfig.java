/* vienhuynhemc */
package com.vienhuynhemc.partition_logic.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.EnableJdbcJobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableBatchProcessing
@EnableJdbcJobRepository
public class BatchConfig {

  @Bean
  public ThreadPoolTaskExecutor partitionTaskExecutor() {
    final ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    taskExecutor.setMaxPoolSize(10);
    taskExecutor.setCorePoolSize(5);
    taskExecutor.setQueueCapacity(10);
    taskExecutor.setThreadNamePrefix("executor-");
    return taskExecutor;
  }
}
