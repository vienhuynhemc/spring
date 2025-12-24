/* vienhuynhemc */
package com.vienhuynhemc.multiple_instances.service;

import com.vienhuynhemc.multiple_instances.entity.EmailOutbox;
import com.vienhuynhemc.multiple_instances.repository.EmailOutboxRepository;
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
