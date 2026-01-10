/* vienhuynhemc */
package com.vienhuynhemc.outbox_pattern;

import java.time.Instant;
import java.util.UUID;

public record OrderStatusUpdatedEvent(
  UUID eventId,
  UUID orderId,
  OrderStatus oldStatus,
  OrderStatus newStatus,
  Instant occurredAt
) {}
