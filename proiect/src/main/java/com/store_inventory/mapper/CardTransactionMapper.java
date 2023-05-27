package com.store_inventory.mapper;

import com.store_inventory.model.CardTransaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CardTransactionMapper {
    private static final CardTransactionMapper INSTANCE = new CardTransactionMapper();

    CardTransactionMapper(){
    }
    public static CardTransactionMapper getInstance(){
        return INSTANCE;
    }
    public Optional<CardTransaction> mapToCardTransaction(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return Optional.of(
                    CardTransaction.builder()
                            .id(UUID.fromString(resultSet.getString("id")))
                            .amount(resultSet.getFloat("amount"))
                            .cardNumber(resultSet.getString("card_number"))
                            .cardExpirationDate(resultSet.getString("expiration_date"))
                            .cardHolderName(resultSet.getString("card_holder_name"))
                            .build()
            );
        } else {
            return Optional.empty();
        }
    }

    public List<CardTransaction> mapToCardTransactionList(ResultSet resultSet) throws SQLException {
        List<CardTransaction> CardTransactionList = new ArrayList<>();

        while(resultSet.next()){
            CardTransactionList.add(mapToCardTransaction(resultSet).get());
        }

        return CardTransactionList;
    }
}
