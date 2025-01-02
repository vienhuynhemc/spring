/* vienhuynhemc */
package com.vienhuynhemc.springdata.redis.lettuce;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootTest
class DriverTest {

  @Autowired
  private RedisConnectionFactory factory;

  @Test
  void shouldConnectToRedisServer() {
    // Arrange
    final RedisTemplate<String, String> template = new RedisTemplate<>();
    template.setConnectionFactory(factory);
    template.setDefaultSerializer(StringRedisSerializer.UTF_8);
    template.afterPropertiesSet();

    // Act
    final String key = UUID.randomUUID().toString();
    template.opsForValue().set(key, "some-value", Duration.ofMinutes(1));

    // Assert
    assertEquals("some-value", template.opsForValue().get(key));
  }
}
