/* vienhuynhemc */
package com.vienhuynhemc.springdata.redis.jedis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class RedisTemplateTest {

  @Autowired
  private RedisOperations<String, String> jedisStringRedisTemplate;

  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  @Test
  void shouldInteractWithRedisTemplate() {
    // Act
    final String key = UUID.randomUUID().toString();
    jedisStringRedisTemplate.opsForValue().set(key, "some-value", Duration.ofMinutes(1));

    // Assert
    assertEquals("some-value", jedisStringRedisTemplate.opsForValue().get(key));
  }

  @Test
  void shouldInteractWithStringRedisTemplate() {
    // Act
    final String key = UUID.randomUUID().toString();
    stringRedisTemplate.opsForValue().set(key, "some-value", Duration.ofMinutes(1));

    // Assert
    assertEquals("some-value", stringRedisTemplate.opsForValue().get(key));
  }
}
