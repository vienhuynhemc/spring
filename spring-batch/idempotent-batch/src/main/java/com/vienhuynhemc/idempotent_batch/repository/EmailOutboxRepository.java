/* vienhuynhemc */
package com.vienhuynhemc.idempotent_batch.repository;

import com.vienhuynhemc.idempotent_batch.entity.EmailOutbox;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailOutboxRepository extends JpaRepository<EmailOutbox, UUID> {}
