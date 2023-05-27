package com.store_inventory.repository;

import com.store_inventory.application.csv.CsvLogger;
import com.store_inventory.config.DatabaseConfiguration;
import com.store_inventory.exceptions.ObjectNotFoundException;
import com.store_inventory.mapper.CategoryMapper;
import com.store_inventory.model.Category;
import com.store_inventory.service.LogServiceImpl;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
public non-sealed class CategoryRepositoryImpl implements CategoryRepository {
    private static final CategoryMapper categoryMapper = CategoryMapper.getInstance();

    @Override
    public Optional<Category> getObjectById (UUID id) throws SQLException, ObjectNotFoundException {

        Connection connection = DatabaseConfiguration.getDbConn();
        String query = "SELECT * FROM category WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setObject(1, id);
            ResultSet resultSet = stmt.executeQuery();
            Optional<Category> category = categoryMapper.mapToCategory(resultSet);

            if (category.isEmpty()) {
                CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: No such category with id " +id));
                throw new ObjectNotFoundException("No such category with id " + id);
            }
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Get category with id " + id + " was done successfully"));
            return category;
        }
    }

    @Override
    public Optional<Category> getObjectByName (String name) throws SQLException {

        Connection connection = DatabaseConfiguration.getDbConn();
        String query = "SELECT * FROM category WHERE name = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, name);
            ResultSet resultSet = stmt.executeQuery();

            return categoryMapper.mapToCategoryList(resultSet).stream().findAny();
        }
    }

    @Override
    public void deleteObjectById(UUID id) {

        String updateNameSql = "DELETE FROM category WHERE id=?";
        Connection connection = DatabaseConfiguration.getDbConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void updateObjectById(UUID id, Category newObject) {

        String updateNameSql = "UPDATE category SET name=?, category_parent=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDbConn();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {

            preparedStatement.setString(1, newObject.getName());
            preparedStatement.setObject(2, newObject.getCategoryParent());
            preparedStatement.setObject(3, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewObject (Category category) {

        Connection connection = DatabaseConfiguration.getDbConn();
        String query = "INSERT INTO category (id, name, category_parent) VALUES(?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, category.getId() != null ? category.getId() : UUID.randomUUID());
            stmt.setString(2, category.getName());
            if (category.getCategoryParent() != null) {
                stmt.setObject(3, category.getCategoryParent());
            } else {
                stmt.setObject(3, null);
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<Category> getAll() {

        String selectSql = "SELECT * FROM category";
        Connection connection = DatabaseConfiguration.getDbConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            return categoryMapper.mapToCategoryList(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return List.of();
    }
    @Override
    public void addAllFromGivenList(List<Category> categoryList) {
        categoryList.forEach(this::addNewObject);
    }

}
