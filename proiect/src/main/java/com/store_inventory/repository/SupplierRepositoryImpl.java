package com.store_inventory.repository;

import com.store_inventory.application.csv.CsvLogger;
import com.store_inventory.config.DatabaseConfiguration;
import com.store_inventory.exceptions.ObjectNotFoundException;
import com.store_inventory.mapper.SupplierMapper;
import com.store_inventory.model.Supplier;
import com.store_inventory.repository.SupplierRepository;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
public class SupplierRepositoryImpl implements SupplierRepository {
    private static final SupplierMapper supplierMapper = SupplierMapper.getInstance();

    @Override
    public Optional<Supplier> getObjectById (UUID id) throws SQLException, ObjectNotFoundException {

        Connection connection = DatabaseConfiguration.getDbConn();
        String query = "SELECT * FROM supplier WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, id.toString());
            ResultSet resultSet = stmt.executeQuery();
            Optional<Supplier> supplier = supplierMapper.mapToSupplier(resultSet);

            if (supplier.isEmpty()) {
                CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: No such supplier with id " +id));
                throw new ObjectNotFoundException("No such supplier with id " + id);
            }
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Get supplier with id " + id + " was done successfully"));
            return supplier;
        }
    }

    @Override
    public Optional<Supplier> getObjectByName (String name) throws SQLException {

        Connection connection = DatabaseConfiguration.getDbConn();
        String query = "SELECT * FROM supplier WHERE name = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, name);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                return SupplierMapper.mapToSupplierList(resultSet).stream().findAny();
            }
        }
        return Optional.empty();
    }

    @Override
    public void deleteObjectById(UUID id) {

        String updateNameSql = "DELETE FROM supplier WHERE id=?";
        Connection connection = DatabaseConfiguration.getDbConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {
            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void updateObjectById(UUID id, Supplier newObject) {

        String updateNameSql = "UPDATE supplier SET name=?, address=?, supplier_type=?, max_stock_capacity=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDbConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {

            preparedStatement.setString(1, newObject.getName());
            preparedStatement.setString(2, newObject.getAddress());
            preparedStatement.setString(3, newObject.getSupplierType().getTypeString());
            preparedStatement.setInt(4, newObject.getMaxStockCapacity());
            preparedStatement.setString(5, id.toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public void addNewObject (Supplier supplier) {

        Connection connection = DatabaseConfiguration.getDbConn();
        String query = "INSERT INTO supplier (id, name, address, supplier_type, max_stock_capacity) VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, supplier.getId().toString());
            stmt.setString(2, supplier.getName());
            stmt.setString(3, supplier.getAddress());
            stmt.setString(4, supplier.getSupplierType().toString());
            stmt.setInt(5, supplier.getMaxStockCapacity());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<Supplier> getAll() {

        String selectSql = "SELECT * FROM supplier";
        Connection connection = DatabaseConfiguration.getDbConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            return supplierMapper.mapToSupplierList(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return List.of();
    }
    @Override
    public void addAllFromGivenList(List<Supplier> supplierList) {
        supplierList.forEach(this::addNewObject);
    }
}
