package com.store_inventory.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConfiguration {
    private static final String DB_URL = "jdbc:postgresql://localhost:5132/postgres";
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

    public static void clearDatabase() throws SQLException {
        String cardSql = "DROP TABLE IF EXISTS CardTransaction";
        String cashSql = "DROP TABLE IF EXISTS CashTransaction";
        String categorySql = "DROP TABLE IF EXISTS Category";
        String locationSql = "DROP TABLE IF EXISTS Location";
        String orderSql = "DROP TABLE IF EXISTS \"Order\"";
        String productSql = "DROP TABLE IF EXISTS Product";
        String stockSql = "DROP TABLE IF EXISTS Stock";
        String supplierSql = "DROP TABLE IF EXISTS Supplier";

        try(Statement delete = dbConn.createStatement()){
            delete.addBatch(cashSql);
            delete.addBatch(categorySql);
            delete.addBatch(locationSql);
            delete.addBatch(orderSql);
            delete.addBatch(stockSql);
            delete.addBatch(productSql);
            delete.addBatch(supplierSql);
            delete.addBatch(cardSql);

            delete.executeBatch();
        }
    }

    public static void initializeDatabase() throws SQLException {
        clearDatabase();

        String cardSql = "CREATE TABLE IF NOT EXISTS CardTransaction (" +
                "    id UUID PRIMARY KEY," +
                "    creation_date DATE," +
                "    amount NUMERIC(15, 3)," +
                "    card_number VARCHAR(40)," +
                "    card_expiration_date DATE" +
                ")";

        String cashSql = "CREATE TABLE IF NOT EXISTS CashTransaction (" +
                "    id UUID PRIMARY KEY," +
                "    creation_date DATE," +
                "    amount NUMERIC(15, 3)" +
                ")";

        String categorySql = "CREATE TABLE IF NOT EXISTS Category (" +
                "    id UUID PRIMARY KEY," +
                "    creation_date DATE," +
                "    name VARCHAR(30)," +
                "    category_parent UUID references Category(id) ON UPDATE CASCADE ON DELETE CASCADE" +
                ")";

        String locationSql = "CREATE TABLE IF NOT EXISTS Location (" +
                "    id UUID PRIMARY KEY," +
                "    creation_date DATE," +
                "    name VARCHAR(60)," +
                "    address VARCHAR(60)," +
                "    type VARCHAR(20)," +
                "    max_stock_capacity INTEGER" +
                ")";

        String supplierSql = "CREATE TABLE IF NOT EXISTS Supplier (" +
                "    id UUID PRIMARY KEY," +
                "    creation_date DATE," +
                "    name VARCHAR(50)," +
                "    address VARCHAR(80)," +
                "    contact_number VARCHAR(20)" +
                ")";

        String orderSql = "CREATE TABLE IF NOT EXISTS \"Order\" (" +
                "    id UUID PRIMARY KEY," +
                "    creation_date DATE," +
                "    order_location_id UUID references Location(id) ON UPDATE CASCADE ON DELETE CASCADE," +
                "    supplier_id UUID references Supplier(id) ON UPDATE CASCADE ON DELETE CASCADE," +
                "    transaction_id UUID," +
                "    total_price NUMERIC(15, 3)" +
                ")";

        String productSql = "CREATE TABLE IF NOT EXISTS Product (" +
                "    id UUID PRIMARY KEY," +
                "    creation_date DATE," +
                "    name VARCHAR(60)," +
                "    category_id UUID references Category(id) ON UPDATE CASCADE ON DELETE CASCADE," +
                "    expiration_date DATE," +
                "    price NUMERIC(15, 3)," +
                "    type VARCHAR(20)," +
                "    expiration_status VARCHAR(20)" +
                ")";

        String stockSql = "CREATE TABLE IF NOT EXISTS Stock (" +
                "    id UUID PRIMARY KEY," +
                "    creation_date DATE," +
                "    product_id UUID references Product(id) ON UPDATE CASCADE ON DELETE CASCADE," +
                "    quantity INTEGER" +
                ")";


        try(Statement create = dbConn.createStatement()){
            create.addBatch(cardSql);
            create.addBatch(cashSql);
            create.addBatch(categorySql);
            create.addBatch(locationSql);
            create.addBatch(supplierSql);
            create.addBatch(orderSql);
            create.addBatch(productSql);
            create.addBatch(stockSql);

            create.executeBatch();
        }
    }
}
