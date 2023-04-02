package com.store_inventory;

import com.store_inventory.application.Menu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            Menu menu = Menu.getInstance();

            menu.showProducts();

            if ("exit".equals(scanner.next())) {
                break;
            }
        }
    }
}