package com.store_inventory.model.records;

import java.time.LocalDateTime;
import java.util.logging.Level;
public record LoggerRecord(Level level, String message, LocalDateTime dateTime) {
}
