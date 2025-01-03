/* vienhuynhemc */
package com.vienhuynhemc.springdata.redis.lettuce;

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
  private RedisOperations<String, String> lettuceStringRedisTemplate;

  @Test
  void shouldConnectToRedisServer() {
    // Act
    final String key = UUID.randomUUID().toString();
    lettuceStringRedisTemplate.opsForValue().set(key, "some-value", Duration.ofMinutes(1));

    // Assert
    assertEquals("some-value", lettuceStringRedisTemplate.opsForValue().get(key));
  }
}
