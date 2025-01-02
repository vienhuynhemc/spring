/* vienhuynhemc */
package com.vienhuynhemc.springdata.redis.lettuce;

import jakarta.annotation.Nonnull;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class LettuceConfiguration {

  @Bean
  public @Nonnull RedisConnectionFactory lettuceConnectionFactory() {
    final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
    config.setHostName("redis-19497.c325.us-east-1-4.ec2.redns.redis-cloud.com");
    config.setPort(19497);
    config.setUsername("default");
    config.setPassword("MslH6Q3QHDyuBT3hWCL5wFkZSrg4o1hl");

    final LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
      .commandTimeout(Duration.ofSeconds(2))
      .build();

    return new LettuceConnectionFactory(config, clientConfig);
  }

  @Bean
  public @Nonnull RedisTemplate<String, String> lettuceStringRedisTemplate(
    @Nonnull RedisConnectionFactory lettuceConnectionFactory
  ) {
    final RedisTemplate<String, String> template = new RedisTemplate<>();
    template.setConnectionFactory(lettuceConnectionFactory);

    return template;
  }

  @Bean
  public @Nonnull StringRedisTemplate stringRedisTemplate(@Nonnull RedisConnectionFactory lettuceConnectionFactory) {
    final StringRedisTemplate template = new StringRedisTemplate();
    template.setConnectionFactory(lettuceConnectionFactory);

    return template;
  }
}
