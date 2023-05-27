package com.store_inventory;

import com.store_inventory.application.Menu;
import com.store_inventory.config.DatabaseConfiguration;
import com.store_inventory.model.Location;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            DatabaseConfiguration.getDbConn();
            DatabaseConfiguration.initializeDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            Menu menu = Menu.getInstance();

            menu.showProducts();
            menu.showLocations();
            menu.showOrders();

            // menu.demoOnThreads();
            // menu.demoOnIterator();
//            menu.demoOnLoggingErrors();
//            menu.demo();
            if ("exit".equals(scanner.next())) {
                break;
            }
        }
    }
}