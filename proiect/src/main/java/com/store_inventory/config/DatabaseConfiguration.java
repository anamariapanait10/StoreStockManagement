package com.store_inventory.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConfiguration {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";
    private static Connection dbConn;

    private DatabaseConfiguration() {
    }

    public static Connection getDbConn() {
        try {
            if (dbConn == null || dbConn.isClosed()) {
                dbConn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dbConn;
    }

    public static void closeDatabaseConnection() {
        try {
            if (dbConn != null && !dbConn.isClosed()) {
                dbConn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
