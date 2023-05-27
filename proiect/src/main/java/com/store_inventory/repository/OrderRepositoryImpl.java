package com.store_inventory.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

public non-sealed class OrderRepositoryImpl implements OrderRepository {
    private static final OrderMapper orderMapper = OrderMapper.getInstance();

    @Override
    public Optional<Order> getObjectById (UUID id) throws SQLException, ObjectNotFoundException {

        Connection connection = DatabaseConfiguration.getDbConn();
        String query = "SELECT * FROM \"Order\" WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setObject(1, id);
            ResultSet resultSet = stmt.executeQuery();
            Optional<Order> order = orderMapper.mapToOrder(resultSet);

            if (order.isEmpty()) {
                CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: No such order with id " +id));
                throw new ObjectNotFoundException("No such order with id " + id);
            }
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Get order with id " + id + " was done successfully"));
            return order;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteObjectById(UUID id) {

        String updateNameSql = "DELETE FROM \"Order\" WHERE id=?";
        Connection connection = DatabaseConfiguration.getDbConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void updateObjectById(UUID id, Order newObject) {

        String updateNameSql = "UPDATE \"Order\" SET order_location_id=?, supplier_id=?, transaction_id=?, total_price=?, orders=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDbConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonOrders = objectMapper.writeValueAsString(newObject.getOrderedProducts());
            preparedStatement.setObject(1, newObject.getOrderLocationId());
            preparedStatement.setObject(2, newObject.getSupplierId());
            preparedStatement.setObject(3, newObject.getTransactionId());
            preparedStatement.setFloat(4, newObject.getTotalPrice());
            preparedStatement.setString(5, jsonOrders);
            preparedStatement.setObject(6, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void addNewObject (Order order) {

        Connection connection = DatabaseConfiguration.getDbConn();
        String query = "INSERT INTO \"Order\" (id, order_location_id, supplier_id, transaction_id, total_price, orders) VALUES(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonOrders = objectMapper.writeValueAsString(order.getOrderedProducts());
            stmt.setObject(1, order.getId() != null ? order.getId() : UUID.randomUUID());
            stmt.setObject(2, order.getOrderLocationId());
            stmt.setObject(3, order.getSupplierId());
            stmt.setObject(4, order.getTransactionId());
            stmt.setFloat(5, order.getTotalPrice());
            stmt.setString(6, jsonOrders);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<Order> getAll() {

        String selectSql = "SELECT * FROM \"Order\"";
        Connection connection = DatabaseConfiguration.getDbConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            return orderMapper.mapToOrderList(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return List.of();
    }
    @Override
    public void addAllFromGivenList(List<Order> orderList) {
        orderList.forEach(this::addNewObject);
    }

    @Override
    public void setOrderProducts(UUID orderId, Map<String, Integer> products) {
        String updateNameSql = "UPDATE \"Order\" SET orders=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDbConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonOrders = objectMapper.writeValueAsString(products);
            preparedStatement.setString(1, jsonOrders);
            preparedStatement.setObject(2, orderId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setOrderTransactionId(UUID orderId, UUID transactionId) {
        String updateNameSql = "UPDATE \"Order\" SET transaction_id=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDbConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {

            preparedStatement.setObject(1, transactionId);
            preparedStatement.setObject(2, orderId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
