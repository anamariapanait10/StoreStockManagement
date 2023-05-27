package com.store_inventory.mapper;

import com.store_inventory.model.Product;
import com.store_inventory.model.enums.ProductType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
public class ProductMapper {
    private static final ProductMapper INSTANCE = new ProductMapper();

    ProductMapper(){
    }

    public static ProductMapper getInstance(){
        return INSTANCE;
    }


    public Optional<Product> mapToProduct(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return Optional.of(
                Product.builder()
                    .id(UUID.fromString(resultSet.getString("id")))
                    .name(resultSet.getString("name"))
                    .price(resultSet.getFloat("price"))
                    .productType(ProductType.valueOf(resultSet.getString("type")))
                    .categoryId(UUID.fromString(resultSet.getString("category_id")))
                    .build()
            );
        } else {
            return Optional.empty();
        }
    }

    public List<Product> mapToProductList(ResultSet resultSet) throws SQLException {
        List<Product> ProductList = new ArrayList<>();

        while(resultSet.next()){
            ProductList.add(mapToProduct(resultSet).get());
        }

        return ProductList;
    }
}
