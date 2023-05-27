package com.store_inventory.repository;

import com.store_inventory.application.csv.CsvLogger;
import com.store_inventory.config.DatabaseConfiguration;
import com.store_inventory.exceptions.ObjectNotFoundException;
import com.store_inventory.mapper.CardTransactionMapper;
import com.store_inventory.model.CardTransaction;
import com.store_inventory.repository.CardTransactionRepository;
import com.store_inventory.service.LogServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

public non-sealed class CardTransactionRepositoryImpl implements CardTransactionRepository {
    private static final CardTransactionMapper cardtransactionMapper = CardTransactionMapper.getInstance();

    @Override
    public Optional<CardTransaction> getObjectById (UUID id) throws SQLException, ObjectNotFoundException {

        Connection connection = DatabaseConfiguration.getDbConn();
        String query = "SELECT * FROM cardtransaction WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, id.toString());
            ResultSet resultSet = stmt.executeQuery();
            Optional<CardTransaction> cardtransaction = cardtransactionMapper.mapToCardTransaction(resultSet);

            if (cardtransaction.isEmpty()) {
                CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: No such cardtransaction with id " +id));
                throw new ObjectNotFoundException("No such cardtransaction with id " + id);
            }
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Get cardtransaction with id " + id + " was done successfully"));
            return cardtransaction;
        }
    }

    @Override
    public Optional<CardTransaction> getObjectByName (String name) throws SQLException {

        Connection connection = DatabaseConfiguration.getDbConn();
        String query = "SELECT * FROM cardtransaction WHERE name = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, name);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                return CardTransactionMapper.mapToCardTransactionList(resultSet).stream().findAny();
            }
        }
        return Optional.empty();
    }

    @Override
    public void deleteObjectById(UUID id) {

        String updateNameSql = "DELETE FROM cardtransaction WHERE id=?";
        Connection connection = DatabaseConfiguration.getDbConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {
            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void updateObjectById(UUID id, CardTransaction newObject) {

        String updateNameSql = "UPDATE cardtransaction SET amount=?, card_number=?, expiration_date=?, card_holder_date=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDbConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {

            preparedStatement.setFloat(1, newObject.getAmount());
            preparedStatement.setString(2, newObject.getCardNumber().toString());
            preparedStatement.setString(3, newObject.getCardExpirationDate().toString());
            preparedStatement.setString(4, newObject.getCardHolderName());
            preparedStatement.setString(5, id.toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNewObject (CardTransaction cardtransaction) {

        Connection connection = DatabaseConfiguration.getDbConn();
        String query = "INSERT INTO cardtransaction (id, amount, card_number, expiration_date, card_holder_date) VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, cardtransaction.getId().toString());
            stmt.setFloat(2, cardtransaction.getAmount());
            stmt.setString(3, cardtransaction.getCardExpirationDate().toString());
            stmt.setString(4, cardtransaction.getCardHolderName());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<CardTransaction> getAll() {

        String selectSql = "SELECT * FROM cardtransaction";
        Connection connection = DatabaseConfiguration.getDbConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            return cardtransactionMapper.mapToCardTransactionList(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return List.of();
    }
    @Override
    public void addAllFromGivenList(List<CardTransaction> cardtransactionList) {
        cardtransactionList.forEach(this::addNewObject);
    }
}
