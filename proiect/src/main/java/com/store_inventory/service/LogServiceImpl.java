package com.store_inventory.service;

import com.store_inventory.model.records.LoggerRecord;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogServiceImpl implements LogService {
    private final Logger logger = Logger.getGlobal();
    private static final LogServiceImpl INSTANCE = new LogServiceImpl();
    private LogServiceImpl() {
    }
    public static LogServiceImpl getInstance() {
        return INSTANCE;
    }
    @Override
    public void log(Level level, String action) {
        logger.log(level, action);
    }
    @Override
    public LoggerRecord logIntoCsv(Level level, String message) {
        return new LoggerRecord(level, message, LocalDateTime.now());
    }
}
