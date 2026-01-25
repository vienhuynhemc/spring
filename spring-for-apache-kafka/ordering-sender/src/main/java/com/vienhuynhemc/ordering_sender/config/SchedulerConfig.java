/* vienhuynhemc */
package com.vienhuynhemc.ordering_sender.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
public class SchedulerConfig {

  @Bean
  public ThreadPoolTaskScheduler taskScheduler() {
    final ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
    taskScheduler.setPoolSize(5);
    taskScheduler.setThreadNamePrefix("scheduler-");
    return taskScheduler;
  }
}
