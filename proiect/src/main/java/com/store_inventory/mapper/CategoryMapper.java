package com.store_inventory.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.store_inventory.model.CashTransaction;
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
        if(resultSet.next()) {
            return Optional.of(
                    Category.builder()
                            .id((UUID) resultSet.getObject("id"))
                            .name(resultSet.getString("name"))
                            .categoryParent((UUID) resultSet.getObject("category_parent"))
                            .build()
            );
        } else {
            return Optional.empty();
        }
    }

    public List<Category> mapToCategoryList(ResultSet resultSet) throws SQLException {
        List<Category> CategoryList = new ArrayList<>();

        while(resultSet.next()){
            CategoryList.add(mapToOneCategory(resultSet).get());
        }

        return CategoryList;
    }

    public Optional<Category> mapToOneCategory(ResultSet resultSet) throws SQLException {
        return Optional.of(
                Category.builder()
                        .id((UUID) resultSet.getObject("id"))
                        .name(resultSet.getString("name"))
                        .categoryParent((UUID) resultSet.getObject("category_parent"))
                        .build()
        );
    }
}
