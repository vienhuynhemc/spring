/* vienhuynhemc */
package com.vienhuynhemc.partition_logic.repository;

import com.vienhuynhemc.partition_logic.entity.EmailOutbox;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailOutboxRepository extends JpaRepository<EmailOutbox, UUID> {}
