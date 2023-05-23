package org.pao;

import java.sql.*;
import java.util.Scanner;

public class BooksTable {

    public static void createBooksTable() {
        String sql = "CREATE TABLE IF NOT EXISTS books (" +
                "id INT PRIMARY KEY," +
                "title VARCHAR(128)," +
                "author VARCHAR(128)," +
                "YEAR int" +
                ")";

        try (Connection conn = PostgresDatabaseConnection.getConnection();
             Statement statement = conn.createStatement()) {

            statement.executeUpdate(sql);
            System.out.println("Books table created");

        } catch (SQLException e) {
            System.err.println("Error creating Books table: " + e.getMessage());
        }
    }

    public static void insertNewBook(String title, String author, int year) {
        String sqlStatement = "INSERT INTO books (id, title, author, year) VALUES (?, ?, ?, ?)";

        int id = getLastBookId() + 1;
        if (id == 0) {
            System.err.println("Error inserting book.");
            return;
        }

        try (Connection connection = PostgresDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, author);
            preparedStatement.setInt(4, year);

            preparedStatement.executeUpdate();

            System.out.println("Inserted book " + title);
        } catch (SQLException e) {
            System.err.println("Error inserting book: " + e.getMessage());
        }
    }

    public static int getLastBookId() {
        String sql = "SELECT MAX(id) FROM books";
        try (Connection connection = PostgresDatabaseConnection.getConnection();
             Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
            else return 0;
        } catch (SQLException e) {
            System.err.println("Error getting last book id: " + e.getMessage());
        }
        return -1;
    }

    public static void printAllBooks() {
        String sql = "SELECT * FROM books";

        try (Connection connection = PostgresDatabaseConnection.getConnection();
             Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                int bookId = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String author = resultSet.getString(3);
                int year = resultSet.getInt(4);

                System.out.println("Book(id=" + bookId + ", title=" + title + ", author=" + author + ", year=" + year + ")");
            }
        } catch (SQLException e) {
            System.err.println("Error reading books from database: " + e.getMessage());
        }
    }

    public static void readAndInsertBook(Scanner scanner) {
        System.out.print("Title= ");
        String title = scanner.nextLine();

        System.out.print("Author= ");
        String author = scanner.nextLine();

        System.out.print("Year= ");
        int year = Integer.parseInt(scanner.nextLine());

        insertNewBook(title, author, year);

    }

    public static void deleteBookFromTable(int id) {
        String sql = "DELETE FROM books WHERE id=?";


        try (Connection conn = PostgresDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            if (preparedStatement.executeUpdate() == 1)
                System.out.println("Deleted book with id= " + id);

        } catch (SQLException e) {
            System.err.println("Error deleting book: " + e.getMessage());
        }
    }

    public static void readBookAndDeleteId(Scanner scanner) {
        System.out.print("Book id to delete= ");
        int id = Integer.parseInt(scanner.nextLine());

        deleteBookFromTable(id);
    }

}
