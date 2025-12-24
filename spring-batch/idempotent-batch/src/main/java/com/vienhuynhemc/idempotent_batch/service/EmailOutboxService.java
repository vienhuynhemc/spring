/* vienhuynhemc */
package com.vienhuynhemc.idempotent_batch.service;

import com.vienhuynhemc.idempotent_batch.entity.EmailOutbox;

public interface EmailOutboxService {
  void saveAll(Iterable<EmailOutbox> emailOutboxes);
}
