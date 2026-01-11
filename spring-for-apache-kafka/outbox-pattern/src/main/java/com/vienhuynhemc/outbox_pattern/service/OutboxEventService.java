/* vienhuynhemc */
package com.vienhuynhemc.outbox_pattern.service;

import com.vienhuynhemc.outbox_pattern.entity.OutboxEvent;
import jakarta.annotation.Nonnull;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

public interface OutboxEventService {
  void saveOutboxEvents(@Nonnull Collection<OutboxEvent> events);

  @Nonnull
  List<OutboxEvent> getValidPendingOutboxEvents(@Nonnull String eventType, @Nonnull Instant timeQuery);
}
