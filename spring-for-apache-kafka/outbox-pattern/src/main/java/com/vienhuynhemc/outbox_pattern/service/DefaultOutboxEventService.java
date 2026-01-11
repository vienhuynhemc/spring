/* vienhuynhemc */
package com.vienhuynhemc.outbox_pattern.service;

import com.vienhuynhemc.outbox_pattern.entity.OutboxEvent;
import com.vienhuynhemc.outbox_pattern.repository.OutboxEventRepository;
import jakarta.annotation.Nonnull;
import java.util.Collection;
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
}
