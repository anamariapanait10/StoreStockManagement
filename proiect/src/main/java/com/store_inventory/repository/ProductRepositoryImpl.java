package com.store_inventory.repository;

import com.store_inventory.application.csv.CsvLogger;
import com.store_inventory.config.DatabaseConfiguration;
import com.store_inventory.exceptions.ObjectNotFoundException;
import com.store_inventory.mapper.ProductMapper;
import com.store_inventory.model.Product;
import com.store_inventory.repository.ProductRepository;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

public non-sealed class ProductRepositoryImpl implements ProductRepository {
    private static final ProductMapper productMapper = ProductMapper.getInstance();

    @Override
    public Optional<Product> getObjectById (UUID id) throws SQLException, ObjectNotFoundException {

        Connection connection = DatabaseConfiguration.getDbConn();
        String query = "SELECT * FROM product WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, id.toString());
            ResultSet resultSet = stmt.executeQuery();
            Optional<Product> product = productMapper.mapToProduct(resultSet);

            if (product.isEmpty()) {
                CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: No such product with id " +id));
                throw new ObjectNotFoundException("No such product with id " + id);
            }
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Get product with id " + id + " was done successfully"));
            return product;
        }
    }
    @Override
    public Optional<Product> getObjectByName (String name) throws SQLException {

        Connection connection = DatabaseConfiguration.getDbConn();
        String query = "SELECT * FROM product WHERE name = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, name);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                return ProductMapper.mapToProductList(resultSet).stream().findAny();
            }
        }
        return Optional.empty();
    }

    @Override
    public void deleteObjectById(UUID id) {

        String updateNameSql = "DELETE FROM product WHERE id=?";
        Connection connection = DatabaseConfiguration.getDbConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {
            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void updateObjectById(UUID id, Product newObject) {

        String updateNameSql = "UPDATE product SET name=?, price=?, category_id=?, expiration_date=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDbConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {
            preparedStatement.setString(1, newObject.getName());
            preparedStatement.setFloat(2, newObject.getPrice());
            preparedStatement.setString(3, newObject.getCategoryId().toString());
            preparedStatement.setString(4, newObject.getExpirationDate().toString());
            preparedStatement.setString(5, id.toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public void addNewObject (Product product) {

        Connection connection = DatabaseConfiguration.getDbConn();
        String query = "INSERT INTO product (id, name, price, type, expiration_date) VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, product.getId().toString());
            stmt.setString(2, product.getName());
            stmt.setFloat(3, product.getPrice());
            stmt.setString(4, product.getCategoryId().toString());
            stmt.setString(5, product.getExpirationDate().toString());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<Product> getAll() {

        String selectSql = "SELECT * FROM product";
        Connection connection = DatabaseConfiguration.getDbConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            return productMapper.mapToProductList(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return List.of();
    }
    @Override
    public void addAllFromGivenList(List<Product> productList) {
        productList.forEach(this::addNewObject);
    }

}
