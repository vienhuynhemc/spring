/* vienhuynhemc */
package com.vienhuynhemc.resume_logic.service;

import com.vienhuynhemc.resume_logic.entity.EmailOutbox;

public interface EmailOutboxService {
  void saveAll(Iterable<EmailOutbox> emailOutboxes);
}
