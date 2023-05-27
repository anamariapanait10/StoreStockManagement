package com.store_inventory.mapper;

import com.store_inventory.model.Order;

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
            return Optional.of(
                    Order.builder()
                            .id(UUID.fromString(resultSet.getString("id")))
                            .supplier(resultSet.getString("supplier"))
                            .orderLocation(resultSet.getString("location"))
                            .totalPrice(resultSet.getFloat("price"))
                            .build()
            );
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
