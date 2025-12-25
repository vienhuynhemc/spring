/* vienhuynhemc */
package com.vienhuynhemc.partition_logic.service;

import com.vienhuynhemc.partition_logic.entity.EmailOutbox;

public interface EmailOutboxService {
  void saveAll(Iterable<EmailOutbox> emailOutboxes);
}
