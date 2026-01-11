/* vienhuynhemc */
package com.vienhuynhemc.outbox_pattern.service;

import com.vienhuynhemc.outbox_pattern.entity.OutboxEvent;
import jakarta.annotation.Nonnull;
import java.util.Collection;

public interface OutboxEventService {
  void saveOutboxEvents(@Nonnull Collection<OutboxEvent> events);
}
