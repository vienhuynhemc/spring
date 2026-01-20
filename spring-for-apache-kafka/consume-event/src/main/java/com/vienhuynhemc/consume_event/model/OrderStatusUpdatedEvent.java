/* vienhuynhemc */
package com.vienhuynhemc.consume_event.model;

import java.time.Instant;
import java.util.UUID;

public record OrderStatusUpdatedEvent(
  UUID eventId,
  UUID orderId,
  OrderStatus oldStatus,
  OrderStatus newStatus,
  Instant occurredAt
) {}
