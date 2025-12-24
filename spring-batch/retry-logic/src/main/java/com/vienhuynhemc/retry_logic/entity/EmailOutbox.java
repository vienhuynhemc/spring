/* vienhuynhemc */
package com.vienhuynhemc.retry_logic.entity;

import com.vienhuynhemc.retry_logic.model.ProcessStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "email_outbox", schema = "public")
public class EmailOutbox {

  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 20)
  private ProcessStatus status;

  @ColumnDefault("0")
  @Column(name = "retry_count", nullable = false)
  private Integer retryCount;

  @Column(name = "last_error", length = Integer.MAX_VALUE)
  private String lastError;

  @Column(name = "next_retry_at")
  private Instant nextRetryAt;

  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @Column(name = "updated_at")
  private Instant updatedAt;
}
