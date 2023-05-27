package com.store_inventory.service;

import com.store_inventory.model.records.LoggerRecord;

import java.security.Timestamp;
import java.util.logging.Level;
import java.util.logging.LogRecord;
public interface LogService {
    void log(Level level, String action);
    LoggerRecord logIntoCSV(Level level, String message);
}
