/* vienhuynhemc */
package com.vienhuynhemc.springdata.redis.jedis.cache;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.time.Duration;
import org.springframework.data.redis.cache.RedisCacheWriter.TtlFunction;

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
