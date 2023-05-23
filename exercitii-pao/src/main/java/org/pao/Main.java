package org.pao;

import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        BooksTable.createBooksTable();
        BooksTable.readAndInsertBook(scanner);
        BooksTable.printAllBooks();
        BooksTable.readAndInsertBook(scanner);
        BooksTable.printAllBooks();
        BooksTable.readBookAndDeleteId(scanner);
        BooksTable.printAllBooks();
        scanner.close();
    }
}