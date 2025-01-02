/* vienhuynhemc */
package com.vienhuynhemc.springdata.redis.jedis;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class JedisConfiguration {

  @Bean
  public @Nonnull RedisConnectionFactory jedisConnectionFactory() {
    final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
    config.setHostName("redis-19497.c325.us-east-1-4.ec2.redns.redis-cloud.com");
    config.setPort(19497);
    config.setUsername("default");
    config.setPassword("MslH6Q3QHDyuBT3hWCL5wFkZSrg4o1hl");

    return new JedisConnectionFactory(config);
  }
}
