/* vienhuynhemc */
package com.vienhuynhemc.idempotent_batch.service;

import com.vienhuynhemc.idempotent_batch.entity.EmailOutbox;
import com.vienhuynhemc.idempotent_batch.repository.EmailOutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultEmailOutboxService implements EmailOutboxService {

  private final EmailOutboxRepository emailOutboxRepository;

  @Override
  public void saveAll(Iterable<EmailOutbox> emailOutboxes) {
    emailOutboxRepository.saveAll(emailOutboxes);
  }
}
