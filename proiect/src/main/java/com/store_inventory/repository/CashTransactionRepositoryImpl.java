package com.store_inventory.repository;

import com.store_inventory.application.csv.CsvLogger;
import com.store_inventory.config.DatabaseConfiguration;
import com.store_inventory.exceptions.ObjectNotFoundException;
import com.store_inventory.mapper.CashTransactionMapper;
import com.store_inventory.model.CashTransaction;
import com.store_inventory.service.LogServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

public non-sealed class CashTransactionRepositoryImpl implements CashTransactionRepository {
    private static final CashTransactionMapper cashtransactionMapper = CashTransactionMapper.getInstance();

    @Override
    public Optional<CashTransaction> getObjectById (UUID id) throws SQLException, ObjectNotFoundException {

        Connection connection = DatabaseConfiguration.getDbConn();
        String query = "SELECT * FROM cashtransaction WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, id.toString());
            ResultSet resultSet = stmt.executeQuery();
            Optional<CashTransaction> cashtransaction = cashtransactionMapper.mapToCashTransaction(resultSet);

            if (cashtransaction.isEmpty()) {
                CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: No such cashtransaction with id " +id));
                throw new ObjectNotFoundException("No such cashtransaction with id " + id);
            }
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Get cashtransaction with id " + id + " was done successfully"));
            return cashtransaction;
        }
    }

    @Override
    public void deleteObjectById(UUID id) {

        String updateNameSql = "DELETE FROM cashtransaction WHERE id=?";
        Connection connection = DatabaseConfiguration.getDbConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {
            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void updateObjectById(UUID id, CashTransaction newObject) {

        String updateNameSql = "UPDATE cashtransaction SET amount=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDbConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {

            preparedStatement.setFloat(1, newObject.getAmount());
            preparedStatement.setString(2, id.toString());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewObject (CashTransaction cashtransaction) {

        Connection connection = DatabaseConfiguration.getDbConn();
        String query = "INSERT INTO cashtransaction (id, amount) VALUES(?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, cashtransaction.getId().toString());
            stmt.setFloat(2, cashtransaction.getAmount());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<CashTransaction> getAll() {

        String selectSql = "SELECT * FROM cashtransaction";
        Connection connection = DatabaseConfiguration.getDbConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            return cashtransactionMapper.mapToCashTransactionList(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return List.of();
    }
    @Override
    public void addAllFromGivenList(List<CashTransaction> cashtransactionList) {
        cashtransactionList.forEach(this::addNewObject);
    }
}
