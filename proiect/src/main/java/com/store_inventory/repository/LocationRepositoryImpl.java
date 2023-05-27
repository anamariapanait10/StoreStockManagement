package com.store_inventory.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store_inventory.application.csv.CsvLogger;
import com.store_inventory.config.DatabaseConfiguration;
import com.store_inventory.exceptions.ObjectNotFoundException;
import com.store_inventory.mapper.LocationMapper;
import com.store_inventory.model.Location;
import com.store_inventory.model.Stock;
import com.store_inventory.service.LogServiceImpl;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
public non-sealed class LocationRepositoryImpl implements LocationRepository {
    private static final LocationMapper locationMapper = LocationMapper.getInstance();

    @Override
    public Optional<Location> getObjectById (UUID id) throws SQLException, ObjectNotFoundException {

        Connection connection = DatabaseConfiguration.getDbConn();
        String query = "SELECT * FROM location WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, id.toString());
            ResultSet resultSet = stmt.executeQuery();
            Optional<Location> location = locationMapper.mapToLocation(resultSet);

            if (location.isEmpty()) {
                CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: No such location with id " +id));
                throw new ObjectNotFoundException("No such location with id " + id);
            }
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Get location with id " + id + " was done successfully"));
            return location;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Location> getObjectByName (String name) throws SQLException {

        Connection connection = DatabaseConfiguration.getDbConn();
        String query = "SELECT * FROM location WHERE name = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, name);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                return locationMapper.mapToLocationList(resultSet).stream().findAny();
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public void setLocationStocks(UUID locationId, List<Stock> stocks) {
        String updateNameSql = "UPDATE location SET stocks=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDbConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonStocks = objectMapper.writeValueAsString(stocks);

            preparedStatement.setString(1, jsonStocks);
            preparedStatement.setObject(2, locationId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteObjectById(UUID id) {

        String updateNameSql = "DELETE FROM location WHERE id=?";
        Connection connection = DatabaseConfiguration.getDbConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {
            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void updateObjectById(UUID id, Location newObject) {

        String updateNameSql = "UPDATE location SET name=?, address=?, type=?, max_stock_capacity=? stocks=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDbConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonStocks = objectMapper.writeValueAsString(newObject.getLocationStocks());
            preparedStatement.setString(1, newObject.getName());
            preparedStatement.setString(2, newObject.getAddress());
            preparedStatement.setString(3, newObject.getLocationType().getTypeString());
            preparedStatement.setInt(4, newObject.getMaxStockCapacity());
            preparedStatement.setString(5, jsonStocks);
            preparedStatement.setString(6, id.toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void addNewObject (Location location) {

        Connection connection = DatabaseConfiguration.getDbConn();
        String query = "INSERT INTO location (id, name, address, type, max_stock_capacity, stocks) VALUES(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonStocks = objectMapper.writeValueAsString(location.getLocationStocks());
            stmt.setObject(1, location.getId() != null ? location.getId() : UUID.randomUUID());
            stmt.setString(2, location.getName());
            stmt.setString(3, location.getAddress());
            stmt.setString(4, location.getLocationType().toString());
            stmt.setInt(5, location.getMaxStockCapacity());
            stmt.setString(6, jsonStocks);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<Location> getAll() {

        String selectSql = "SELECT * FROM location";
        Connection connection = DatabaseConfiguration.getDbConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            return locationMapper.mapToLocationList(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return List.of();
    }
    @Override
    public void addAllFromGivenList(List<Location> locationList) {
        locationList.forEach(this::addNewObject);
    }
}
