package com.store_inventory.repository;

import com.store_inventory.application.csv.CsvLogger;
import com.store_inventory.config.DatabaseConfiguration;
import com.store_inventory.exceptions.ObjectNotFoundException;
import com.store_inventory.mapper.OrderMapper;
import com.store_inventory.model.Order;
import com.store_inventory.service.LogServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

public non-sealed class OrderRepositoryImpl implements OrderRepository {
    private static final OrderMapper orderMapper = OrderMapper.getInstance();

    @Override
    public Optional<Order> getObjectById (UUID id) throws SQLException, ObjectNotFoundException {

        Connection connection = DatabaseConfiguration.getDbConn();
        String query = "SELECT * FROM order WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, id.toString());
            ResultSet resultSet = stmt.executeQuery();
            Optional<Order> order = orderMapper.mapToOrder(resultSet);

            if (order.isEmpty()) {
                CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: No such order with id " +id));
                throw new ObjectNotFoundException("No such order with id " + id);
            }
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Get order with id " + id + " was done successfully"));
            return order;
        }
    }

    @Override
    public void deleteObjectById(UUID id) {

        String updateNameSql = "DELETE FROM order WHERE id=?";
        Connection connection = DatabaseConfiguration.getDbConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {
            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void updateObjectById(UUID id, Order newObject) {

        String updateNameSql = "UPDATE order SET supplier=?, location=?, price=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDbConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {

            preparedStatement.setString(1, newObject.getSupplierId().toString());
            preparedStatement.setString(2, newObject.getOrderLocationId().toString());
            preparedStatement.setFloat(3, newObject.getTotalPrice());
            preparedStatement.setString(4, id.toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNewObject (Order order) {

        Connection connection = DatabaseConfiguration.getDbConn();
        String query = "INSERT INTO order (id, supplier, location, price) VALUES(?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, order.getId().toString());
            stmt.setString(2, order.getSupplierId().toString());
            stmt.setString(3, order.getOrderLocationId().toString());
            stmt.setFloat(4, order.getTotalPrice());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<Order> getAll() {

        String selectSql = "SELECT * FROM order";
        Connection connection = DatabaseConfiguration.getDbConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            return orderMapper.mapToOrderList(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return List.of();
    }
    @Override
    public void addAllFromGivenList(List<Order> orderList) {
        orderList.forEach(this::addNewObject);
    }
}
