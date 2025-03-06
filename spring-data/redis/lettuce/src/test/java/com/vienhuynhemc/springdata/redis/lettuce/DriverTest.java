/* vienhuynhemc */
package com.vienhuynhemc.springdata.redis.lettuce;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.RedisOperations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class DriverTest {

  @Autowired
  private RedisOperations<String, String> lettuceStringRedisTemplate;

  @Autowired
  private ReactiveRedisOperations<String, String> lettuceReactiveStringRedisTemplate;

  @Test
  void shouldConnectToRedisServer() {
    // Act
    final String key = UUID.randomUUID().toString();
    lettuceStringRedisTemplate.opsForValue().set(key, "some-value", Duration.ofMinutes(1));

    // Assert
    assertEquals("some-value", lettuceStringRedisTemplate.opsForValue().get(key));
  }

  @Test
  void shouldConnectToRedisServer_whenUsingReactive() {
    // Act
    final String key = UUID.randomUUID().toString();
    final Mono<Boolean> isSaved = lettuceReactiveStringRedisTemplate
      .opsForValue()
      .set(key, "some-value", Duration.ofMinutes(1));

    // Assert
    StepVerifier.create(isSaved).expectNext(true).verifyComplete();
    StepVerifier.create(lettuceReactiveStringRedisTemplate.opsForValue().get(key))
      .expectNext("some-value")
      .verifyComplete();
  }
}
