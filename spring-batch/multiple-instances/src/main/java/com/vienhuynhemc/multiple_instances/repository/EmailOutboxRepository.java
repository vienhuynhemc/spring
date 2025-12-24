/* vienhuynhemc */
package com.vienhuynhemc.multiple_instances.repository;

import com.vienhuynhemc.multiple_instances.entity.EmailOutbox;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailOutboxRepository extends JpaRepository<EmailOutbox, UUID> {}
