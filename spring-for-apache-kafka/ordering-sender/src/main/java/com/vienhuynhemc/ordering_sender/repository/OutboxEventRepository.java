/* vienhuynhemc */
package com.vienhuynhemc.ordering_sender.repository;

import com.vienhuynhemc.ordering_sender.entity.OutboxEvent;
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
    WHERE e.sequenceNumber IS NULL
      AND e.eventType = :eventType
      AND e.retryCount < 3
      AND e.status != 'SENT'
      AND e.nextRetryAt <= :timeQuery
    """
  )
  @Nonnull
  List<OutboxEvent> getValidPendingOutboxEvents(@Nonnull String eventType, @Nonnull Instant timeQuery);

  @Query(
    value = """
    WITH
      pendingEvents AS (
        SELECT
          *,
          ROW_NUMBER() OVER (
            PARTITION BY
              aggregate_id
            ORDER BY
              sequence_number
          ) AS RANK
        FROM
          outbox_event
        WHERE
          status != 'SENT'
          AND event_type = :eventType
          AND sequence_number IS NOT NULL
      )
    SELECT
      *
    FROM
      pendingEvents
    WHERE
      RANK = 1
      AND retry_count < 3
      AND next_retry_at <= :timeQuery
    """,
    nativeQuery = true
  )
  @Nonnull
  List<OutboxEvent> getValidPendingPerKeyOutboxEvents(@Nonnull String eventType, @Nonnull Instant timeQuery);
}
