CREATE TABLE IF NOT EXISTS outbox_event (
  id UUID,
  event_type VARCHAR(200) NOT NULL,
  aggregate_id VARCHAR(200) NOT NULL,
  payload JSONB NOT NULL,
  status VARCHAR(20) NOT NULL,
  retry_count INT NOT NULL DEFAULT 0,
  next_retry_at TIMESTAMP NULL,
  error_message TEXT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  send_at TIMESTAMP NULL,
  PRIMARY KEY (id),
  CHECK (status IN ('PENDING', 'SENT', 'FAILED'))
);
