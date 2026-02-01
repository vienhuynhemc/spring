/* vienhuynhemc */
package com.vienhuynhemc.ordering_sender.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vienhuynhemc.ordering_sender.entity.OutboxEvent;
import com.vienhuynhemc.ordering_sender.model.OrderStatus;
import com.vienhuynhemc.ordering_sender.model.OrderStatusUpdatedEvent;
import com.vienhuynhemc.ordering_sender.model.OutboxEventStatus;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultOrderStatusUpdatedService implements OrderStatusUpdatedService {

  private final Random random = new Random();
  private final ObjectMapper objectMapper;
  private final OutboxEventService outboxEventService;

  @Override
  @Transactional
  public void processOrder() {
    generateAndSaveOutboxEvents();

    if (random.nextBoolean()) {
      throw new RuntimeException("Some error occurred while processing order");
    }
  }

  @Override
  public @Nonnull ProducerRecord<String, OrderStatusUpdatedEvent> createRecord(@Nonnull OutboxEvent event) {
    final OrderStatusUpdatedEvent payload;
    try {
      payload = objectMapper.treeToValue(event.getPayload(), OrderStatusUpdatedEvent.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    return new ProducerRecord<>(
      event.getEventType(),
      null,
      Instant.now().toEpochMilli(),
      event.getAggregateId(),
      payload
    );
  }

  private void generateAndSaveOutboxEvents() {
    final List<OutboxEvent> outboxEvents = generateOrderStatusUpdatedEvents()
      .stream()
      .map(this::toOutboxEvent)
      .toList();

    outboxEventService.saveOutboxEvents(outboxEvents);
  }

  private @Nonnull OutboxEvent toOutboxEvent(@Nonnull OrderStatusUpdatedEvent event) {
    final int sequenceNumber = getSequenceNumber(event.newStatus());

    return OutboxEvent.builder()
      .id(UUID.randomUUID())
      .eventType("order.status.v1")
      .aggregateId(event.orderId().toString())
      .payload(objectMapper.valueToTree(event))
      .status(OutboxEventStatus.PENDING)
      .retryCount(0)
      .nextRetryAt(Instant.now())
      .sequenceNumber(sequenceNumber)
      .createdAt(Instant.now())
      .build();
  }

  private @Nonnull List<OrderStatusUpdatedEvent> generateOrderStatusUpdatedEvents() {
    final List<OrderStatusUpdatedEvent> events = new ArrayList<>();
    final UUID orderId = UUID.randomUUID();

    events.add(
      new OrderStatusUpdatedEvent(UUID.randomUUID(), orderId, OrderStatus.DRAFT, OrderStatus.PENDING, Instant.now())
    );

    boolean isHaveSubmitted = random.nextBoolean();
    if (isHaveSubmitted) {
      final UUID eventId = UUID.randomUUID();
      events.add(
        new OrderStatusUpdatedEvent(eventId, orderId, OrderStatus.PENDING, OrderStatus.SUBMITTED, Instant.now())
      );
    }

    boolean isHaveComplete = random.nextBoolean();
    if (isHaveComplete) {
      final UUID eventId = UUID.randomUUID();
      events.add(
        new OrderStatusUpdatedEvent(eventId, orderId, OrderStatus.SUBMITTED, OrderStatus.COMPLETED, Instant.now())
      );
    }

    return events;
  }

  private int getSequenceNumber(@Nonnull OrderStatus status) {
    return switch (status) {
      case DRAFT -> 0;
      case PENDING -> 1;
      case SUBMITTED -> 2;
      case COMPLETED -> 3;
    };
  }
}
