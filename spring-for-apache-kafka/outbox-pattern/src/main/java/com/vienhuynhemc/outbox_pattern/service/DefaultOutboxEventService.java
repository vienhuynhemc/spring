/* vienhuynhemc */
package com.vienhuynhemc.outbox_pattern.service;

import com.vienhuynhemc.outbox_pattern.entity.OutboxEvent;
import com.vienhuynhemc.outbox_pattern.repository.OutboxEventRepository;
import jakarta.annotation.Nonnull;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultOutboxEventService implements OutboxEventService {

  private final OutboxEventRepository outboxEventRepository;

  @Override
  public void saveOutboxEvents(@Nonnull Collection<OutboxEvent> events) {
    outboxEventRepository.saveAll(events);
  }

  @Override
  public @Nonnull List<OutboxEvent> getValidPendingOutboxEvents(@Nonnull String eventType, @Nonnull Instant timeQuery) {
    return outboxEventRepository.getValidPendingOutboxEvents(eventType, timeQuery);
  }
}
