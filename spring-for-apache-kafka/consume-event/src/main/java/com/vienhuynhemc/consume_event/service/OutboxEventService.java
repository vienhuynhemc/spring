/* vienhuynhemc */
package com.vienhuynhemc.consume_event.service;

import com.vienhuynhemc.consume_event.entity.OutboxEvent;
import jakarta.annotation.Nonnull;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

public interface OutboxEventService {
  void saveOutboxEvents(@Nonnull Collection<OutboxEvent> events);

  @Nonnull
  List<OutboxEvent> getValidPendingOutboxEvents(@Nonnull String eventType, @Nonnull Instant timeQuery);

  @Nonnull
  List<OutboxEvent> getValidPendingPerKeyOutboxEvents(@Nonnull String eventType, @Nonnull Instant timeQuery);

  void markSent(@Nonnull OutboxEvent event);

  void markFailed(@Nonnull OutboxEvent event, @Nonnull Throwable exception);
}
