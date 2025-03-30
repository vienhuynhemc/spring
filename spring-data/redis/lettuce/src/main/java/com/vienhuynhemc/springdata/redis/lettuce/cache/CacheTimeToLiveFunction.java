/* vienhuynhemc */
package com.vienhuynhemc.springdata.redis.lettuce.cache;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.data.redis.cache.RedisCacheWriter.TtlFunction;

import java.time.Duration;

public class CacheTimeToLiveFunction implements TtlFunction {

  @Override
  public @Nonnull Duration getTimeToLive(@Nonnull Object key, @Nullable Object value) {
    if (key instanceof String k) {
      if (k.startsWith("my-book.")) {
        return Duration.ofMinutes(10);
      }
    }

    return Duration.ofMinutes(5);
  }
}
