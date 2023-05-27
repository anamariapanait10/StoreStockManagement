package com.store_inventory.repository;

import com.store_inventory.application.csv.CsvLogger;
import com.store_inventory.config.DatabaseConfiguration;
import com.store_inventory.exceptions.ObjectNotFoundException;
import com.store_inventory.mapper.StockMapper;
import com.store_inventory.model.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

public class StockRepositoryImpl {
    private static final StockMapper stockMapper = StockMapper.getInstance();

    @Override
    public Optional<Stock> getObjectById (UUID id) throws SQLException, ObjectNotFoundException {

        Connection connection = DatabaseConfiguration.getDbConn();
        String query = "SELECT * FROM stock WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, id.toString());
            ResultSet resultSet = stmt.executeQuery();
            Optional<Stock> stock = stockMapper.mapToStock(resultSet);

            if (stock.isEmpty()) {
                CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: No such stock with id " +id));
                throw new ObjectNotFoundException("No such stock with id " + id);
            }
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Get stock with id " + id + " was done successfully"));
            return stock;
        }
    }

    @Override
    public Optional<Stock> getObjectByName (String name) throws SQLException {

        Connection connection = DatabaseConfiguration.getDbConn();
        String query = "SELECT * FROM stock WHERE name = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, name);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                return StockMapper.mapToStockList(resultSet).stream().findAny();
            }
        }
        return Optional.empty();
    }

    @Override
    public void deleteObjectById(UUID id) {

        String updateNameSql = "DELETE FROM stock WHERE id=?";
        Connection connection = DatabaseConfiguration.getDbConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {
            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void updateObjectById(UUID id, Stock newObject) {

        String updateNameSql = "UPDATE stock SET product=?, quantity=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDbConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {

            preparedStatement.setString(1, newObject.getProduct().getName());
            preparedStatement.setInt(2, newObject.getProductQuantity());
            preparedStatement.setString(3, id.toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNewObject (Stock stock) {

        Connection connection = DatabaseConfiguration.getDbConn();
        String query = "INSERT INTO stock (id, product, quantity) VALUES(?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, stock.getId().toString());
            stmt.setString(2, stock.getProduct().getName());
            stmt.setInt(3, stock.getProductQuantity());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<Stock> getAll() {

        String selectSql = "SELECT * FROM stock";
        Connection connection = DatabaseConfiguration.getDbConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            return stockMapper.mapToStockList(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return List.of();
    }
    @Override
    public void addAllFromGivenList(List<Stock> stockList) {
        stockList.forEach(this::addNewObject);
    }
}
