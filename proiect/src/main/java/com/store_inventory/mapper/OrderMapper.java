package com.store_inventory.mapper;

import com.store_inventory.model.Order;
import com.store_inventory.model.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OrderMapper {
    private static final OrderMapper INSTANCE = new OrderMapper();

    OrderMapper(){
    }
    public static OrderMapper getInstance(){
        return INSTANCE;
    }
    public Optional<Order> mapToOrder(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            Order obj = Order.builder()
                    .orderLocationId(UUID.fromString(resultSet.getString("locationId")))
                    .supplierId(UUID.fromString(resultSet.getString("supplierId")))
                    .transactionId(UUID.fromString(resultSet.getString("transactionId")))
                    .totalPrice(resultSet.getFloat("price"))
                    .build();
            obj.setId(UUID.fromString(resultSet.getString("id")));
            return Optional.of(obj);
        } else {
            return Optional.empty();
        }
    }

    public List<Order> mapToOrderList(ResultSet resultSet) throws SQLException {
        List<Order> OrderList = new ArrayList<>();

        while(resultSet.next()){
            OrderList.add(mapToOrder(resultSet).get());
        }

        return OrderList;
    }
}