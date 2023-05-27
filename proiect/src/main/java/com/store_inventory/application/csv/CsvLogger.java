package com.store_inventory.application.csv;

import com.store_inventory.model.records.LoggerRecord;
import com.store_inventory.service.LogServiceImpl;

import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;

import static com.store_inventory.application.utils.Constants.CSV_WRITE_PATH;

public class CsvLogger {
    private static CsvLogger INSTANCE;
    private static final CsvWriter CSV_WRITER = CsvWriter.getInstance();
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private CsvLogger(){
    }
    public static CsvLogger getInstance(){
        if(INSTANCE == null){
            INSTANCE = new CsvLogger();
        }
        return INSTANCE;
    }

    public String[] format(LoggerRecord record) {
        return new String[] {TIMESTAMP_FORMATTER.format(record.dateTime()), record.level().toString(), record.message()};
    }

    public void logAction(LoggerRecord record){
        try {
            CSV_WRITER.writeLine(format(record), Path.of(CSV_WRITE_PATH));
        } catch (Exception e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

}
