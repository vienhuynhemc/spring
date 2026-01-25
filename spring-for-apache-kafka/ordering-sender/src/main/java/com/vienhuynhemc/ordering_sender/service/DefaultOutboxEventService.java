/* vienhuynhemc */
package com.vienhuynhemc.ordering_sender.service;

import com.vienhuynhemc.ordering_sender.entity.OutboxEvent;
import com.vienhuynhemc.ordering_sender.model.OutboxEventStatus;
import com.vienhuynhemc.ordering_sender.repository.OutboxEventRepository;
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

  @Override
  public void markSent(@Nonnull OutboxEvent event) {
    event.setStatus(OutboxEventStatus.SENT);
    event.setSendAt(Instant.now());

    outboxEventRepository.save(event);
  }

  @Override
  public void markFailed(@Nonnull OutboxEvent event, @Nonnull Throwable throwable) {
    event.setStatus(OutboxEventStatus.FAILED);
    event.setRetryCount(event.getRetryCount() + 1);
    event.setNextRetryAt(Instant.now());
    event.setErrorMessage(throwable.getMessage());

    outboxEventRepository.save(event);
  }
}
