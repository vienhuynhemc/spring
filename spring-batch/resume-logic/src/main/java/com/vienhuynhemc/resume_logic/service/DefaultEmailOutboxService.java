/* vienhuynhemc */
package com.vienhuynhemc.resume_logic.service;

import com.vienhuynhemc.resume_logic.entity.EmailOutbox;
import com.vienhuynhemc.resume_logic.repository.EmailOutboxRepository;
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
