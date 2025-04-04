/* vienhuynhemc */
package com.vienhuynhemc.springdata.redis.jedis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisOperations;

@SpringBootTest
class DriverTest {

  @Autowired
  private RedisOperations<String, String> jedisStringRedisTemplate;

  @Test
  void shouldConnectToRedisServer() {
    // Act
    final String key = UUID.randomUUID().toString();
    jedisStringRedisTemplate.opsForValue().set(key, "some-value", Duration.ofMinutes(1));

    // Assert
    assertEquals("some-value", jedisStringRedisTemplate.opsForValue().get(key));
  }
}
