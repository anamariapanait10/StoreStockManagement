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
            Product obj = Product.builder()
                    .name(resultSet.getString("name"))
                    .price(resultSet.getFloat("price"))
                    .productType(ProductType.valueOf(resultSet.getString("type")))
                    .categoryId(UUID.fromString(resultSet.getString("category_id")))
                    .build();
            obj.setId(UUID.fromString(resultSet.getString("id")));
            return Optional.of(obj);
        } else {
            return Optional.empty();
        }
    }

    private Optional<Product> mapToOneProduct(ResultSet resultSet) throws SQLException {
        Product obj = Product.builder()
                .name(resultSet.getString("name"))
                .price(resultSet.getFloat("price"))
                .productType(ProductType.valueOf(resultSet.getString("type")))
                .categoryId(UUID.fromString(resultSet.getString("category_id")))
                .expirationStatus(resultSet.getString("expiration_status"))
                .build();
        obj.setId(UUID.fromString(resultSet.getString("id")));
        return Optional.of(obj);
    }

    public List<Product> mapToProductList(ResultSet resultSet) throws SQLException {
        List<Product> ProductList = new ArrayList<>();

        while(resultSet.next()){
            ProductList.add(mapToOneProduct(resultSet).get());
        }

        return ProductList;
    }
}
