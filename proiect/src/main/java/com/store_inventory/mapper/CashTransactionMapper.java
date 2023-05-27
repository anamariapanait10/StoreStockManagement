package com.store_inventory.mapper;

import com.store_inventory.model.CashTransaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CashTransactionMapper {
    private static final CashTransactionMapper INSTANCE = new CashTransactionMapper();

    CashTransactionMapper(){
    }
    public static CashTransactionMapper getInstance(){
        return INSTANCE;
    }
    public Optional<CashTransaction> mapToCashTransaction(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return Optional.of(
                    CashTransaction.builder()
                            .id(UUID.fromString(resultSet.getString("id")))
                            .amount(resultSet.getFloat("amount"))
                            .build()
            );
        } else {
            return Optional.empty();
        }
    }

    public List<CashTransaction> mapToCashTransactionList(ResultSet resultSet) throws SQLException {
        List<CashTransaction> CashTransactionList = new ArrayList<>();

        while(resultSet.next()){
            CashTransactionList.add(mapToCashTransaction(resultSet).get());
        }

        return CashTransactionList;
    }
}
