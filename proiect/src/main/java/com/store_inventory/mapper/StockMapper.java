package com.store_inventory.mapper;

import com.store_inventory.model.Product;
import com.store_inventory.model.Stock;
import com.store_inventory.model.enums.ProductType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class StockMapper {
    private static final StockMapper INSTANCE = new StockMapper();

    StockMapper(){
    }
    public static StockMapper getInstance(){
        return INSTANCE;
    }
    public Optional<Stock> mapToStock(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            Stock obj = Stock.builder()
                    .productId(UUID.fromString(resultSet.getString("product_id")))
                    .productQuantity(resultSet.getInt("quantity"))
                    .build();
            obj.setId(UUID.fromString(resultSet.getString("id")));
            return Optional.of(obj);
        } else {
            return Optional.empty();
        }
    }

    public List<Stock> mapToStockList(ResultSet resultSet) throws SQLException {
        List<Stock> StockList = new ArrayList<>();

        while(resultSet.next()){
            StockList.add(mapToOneStock(resultSet).get());
        }

        return StockList;
    }

    private Optional<Stock> mapToOneStock(ResultSet resultSet) throws SQLException {
        Stock obj = Stock.builder()
                .productId(UUID.fromString(resultSet.getString("product_id")))
                .productQuantity(resultSet.getInt("quantity"))
                .build();
        obj.setId(UUID.fromString(resultSet.getString("id")));
        return Optional.of(obj);
    }
}