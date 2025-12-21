/* vienhuynhemc */
package com.vienhuynhemc.idempotent_batch.service;

public interface EmailOutboxService {
  void triggerEmailBatchJob();
}
