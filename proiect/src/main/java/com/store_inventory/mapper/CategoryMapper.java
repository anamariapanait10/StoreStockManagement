package com.store_inventory.mapper;

import com.store_inventory.model.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CategoryMapper {
    private static final CategoryMapper INSTANCE = new CategoryMapper();

    CategoryMapper(){
    }
    public static CategoryMapper getInstance(){
        return INSTANCE;
    }
    public Optional<Category> mapToCategory(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return Optional.of(
                    Category.builder()
                            .id(UUID.fromString(resultSet.getString("id")))
                            .name(resultSet.getString("name"))
                            .build()
            );
        } else {
            return Optional.empty();
        }
    }

    public List<Category> mapToCategoryList(ResultSet resultSet) throws SQLException {
        List<Category> CategoryList = new ArrayList<>();

        while(resultSet.next()){
            CategoryList.add(mapToCategory(resultSet).get());
        }

        return CategoryList;
    }
}
