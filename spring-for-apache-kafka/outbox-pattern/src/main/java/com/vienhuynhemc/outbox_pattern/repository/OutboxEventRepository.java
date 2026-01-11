/* vienhuynhemc */
package com.vienhuynhemc.outbox_pattern.repository;

import com.vienhuynhemc.outbox_pattern.entity.OutboxEvent;
import jakarta.annotation.Nonnull;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {
  @Query(
    value = """
    SELECT e FROM OutboxEvent e
    WHERE e.eventType = :eventType
      AND e.retryCount < 3
      AND e.status != 'SENT'
      AND e.nextRetryAt <= :timeQuery
    ORDER BY e.createdAt
    """
  )
  @Nonnull
  List<OutboxEvent> getValidPendingOutboxEvents(@Nonnull String eventType, @Nonnull Instant timeQuery);
}
