package org.pao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDatabaseConnection {
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/library";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "admin";
    private static Connection dbConn;

    private PostgresDatabaseConnection() {
    }

    public static Connection getConnection() {
        try {
            if (dbConn == null || dbConn.isClosed()) {
                dbConn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return dbConn;
    }

    public static void closeConnection() {
        try {
            if (dbConn != null && !dbConn.isClosed()) {
                dbConn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
