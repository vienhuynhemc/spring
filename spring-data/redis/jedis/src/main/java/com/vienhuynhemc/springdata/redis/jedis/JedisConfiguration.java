/* vienhuynhemc */
package com.vienhuynhemc.springdata.redis.jedis;

import jakarta.annotation.Nonnull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class JedisConfiguration {

  @Bean
  public @Nonnull RedisConnectionFactory jedisConnectionFactory() {
    final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
    config.setHostName("redis-13844.c292.ap-southeast-1-1.ec2.redns.redis-cloud.com");
    config.setPort(13844);
    config.setUsername("default");
    config.setPassword("6vPcMcT8FxK06QizUZhI66Rd9W1xXCbW");

    return new JedisConnectionFactory(config);
  }

  @Bean
  public @Nonnull RedisTemplate<String, String> jedisStringRedisTemplate(
    @Nonnull RedisConnectionFactory jedisConnectionFactory
  ) {
    final RedisTemplate<String, String> template = new RedisTemplate<>();
    template.setConnectionFactory(jedisConnectionFactory);
    template.setDefaultSerializer(RedisSerializer.string());

    return template;
  }

  @Bean
  public @Nonnull StringRedisTemplate stringRedisTemplate(@Nonnull RedisConnectionFactory jedisConnectionFactory) {
    final StringRedisTemplate template = new StringRedisTemplate();
    template.setConnectionFactory(jedisConnectionFactory);

    return template;
  }
}
