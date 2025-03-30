/* vienhuynhemc */
package com.vienhuynhemc.springdata.redis.lettuce;

import com.vienhuynhemc.springdata.redis.lettuce.cache.CacheTimeToLiveFunction;
import jakarta.annotation.Nonnull;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.BatchStrategies;
import org.springframework.data.redis.cache.BatchStrategy;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class LettuceConfig {

  @Bean
  public @Nonnull RedisConnectionFactory lettuceConnectionFactory() {
    final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
    config.setHostName("redis-13844.c292.ap-southeast-1-1.ec2.redns.redis-cloud.com");
    config.setPort(13844);
    config.setUsername("default");
    config.setPassword("6vPcMcT8FxK06QizUZhI66Rd9W1xXCbW");

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
    template.setDefaultSerializer(RedisSerializer.string());

    return template;
  }

  @Bean
  public @Nonnull StringRedisTemplate stringRedisTemplate(@Nonnull RedisConnectionFactory lettuceConnectionFactory) {
    final StringRedisTemplate template = new StringRedisTemplate();
    template.setConnectionFactory(lettuceConnectionFactory);

    return template;
  }

  @Bean
  public @Nonnull ReactiveRedisTemplate<String, String> lettuceReactiveStringRedisTemplate(
    @Nonnull ReactiveRedisConnectionFactory lettuceConnectionFactory
  ) {
    return new ReactiveRedisTemplate<>(lettuceConnectionFactory, RedisSerializationContext.string());
  }

  @Bean
  public @Nonnull ReactiveStringRedisTemplate reactiveStringRedisTemplate(
    @Nonnull ReactiveRedisConnectionFactory lettuceConnectionFactory
  ) {
    return new ReactiveStringRedisTemplate(lettuceConnectionFactory);
  }

  @Bean
  public @Nonnull RedisCacheManager cacheManager(@Nonnull RedisConnectionFactory lettuceConnectionFactory) {
    final BatchStrategy batchStrategies = BatchStrategies.scan(5);
    final RedisCacheWriter redisCacheWriter = RedisCacheWriter.lockingRedisCacheWriter(
      lettuceConnectionFactory,
      batchStrategies
    );

    final RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
      .computePrefixWith(cacheName -> "springdata.redis.lettuce." + cacheName + ".")
      .entryTtl(new CacheTimeToLiveFunction())
      .enableTimeToIdle()
      .disableCachingNullValues();

    return RedisCacheManager.builder(redisCacheWriter)
      .cacheDefaults(config)
      .transactionAware()
      .enableStatistics()
      .build();
  }
}
