/* vienhuynhemc */
package com.vienhuynhemc.ordering_sender.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.vienhuynhemc.ordering_sender.model.OutboxEventStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Entity
@Table(name = "outbox_event", schema = "public")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutboxEvent {

  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "event_type", nullable = false, length = 200)
  private String eventType;

  @Column(name = "aggregate_id", nullable = false, length = 200)
  private String aggregateId;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "payload", nullable = false)
  private JsonNode payload;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 20)
  private OutboxEventStatus status;

  @ColumnDefault("0")
  @Column(name = "retry_count", nullable = false)
  private Integer retryCount;

  @Column(name = "next_retry_at")
  private Instant nextRetryAt;

  @Column(name = "error_message", length = Integer.MAX_VALUE)
  private String errorMessage;

  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @Column(name = "send_at")
  private Instant sendAt;
}
