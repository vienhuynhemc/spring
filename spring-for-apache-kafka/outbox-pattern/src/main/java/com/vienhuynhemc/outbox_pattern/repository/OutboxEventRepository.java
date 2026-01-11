/* vienhuynhemc */
package com.vienhuynhemc.outbox_pattern.repository;

import com.vienhuynhemc.outbox_pattern.entity.OutboxEvent;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {}
