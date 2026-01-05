/* vienhuynhemc */
package com.vienhuynhemc.produce_event;

import java.time.Instant;

public record OrderStatusUpdatedEvent(
  String eventId,
  String orderId,
  String oldStatus,
  String newStatus,
  Instant occurredAt
) {}
