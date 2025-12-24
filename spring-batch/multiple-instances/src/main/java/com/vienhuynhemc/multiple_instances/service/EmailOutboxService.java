/* vienhuynhemc */
package com.vienhuynhemc.multiple_instances.service;

import com.vienhuynhemc.multiple_instances.entity.EmailOutbox;

public interface EmailOutboxService {
  void saveAll(Iterable<EmailOutbox> emailOutboxes);
}
