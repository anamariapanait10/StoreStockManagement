package com.store_inventory.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store_inventory.model.CardTransaction;
import com.store_inventory.model.Order;
import com.store_inventory.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CardTransactionMapper {
    private static final CardTransactionMapper INSTANCE = new CardTransactionMapper();

    CardTransactionMapper(){
    }
    public static CardTransactionMapper getInstance(){
        return INSTANCE;
    }
    public Optional<CardTransaction> mapToCardTransaction(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(resultSet.getString("card_expiration_date"), formatter);

            CardTransaction obj = CardTransaction.builder()
                    .amount(resultSet.getFloat("amount"))
                    .cardNumber(resultSet.getString("card_number"))
                    .cardExpirationDate(date)
                    .cardHolderName(resultSet.getString("card_holder_name"))
                    .build();

            obj.setId(UUID.fromString(resultSet.getString("id")));

            return Optional.of(obj);
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

    private Optional<CardTransaction> mapToOneCardTransaction(ResultSet resultSet) throws SQLException, JsonProcessingException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        formatter = formatter.withLocale(Locale.ENGLISH);
        LocalDate date = LocalDate.parse(resultSet.getString("expiration_date"), formatter);

        CardTransaction obj = CardTransaction.builder()
                .amount(resultSet.getFloat("amount"))
                .cardNumber(resultSet.getString("card_number"))
                .cardExpirationDate(date)
                .cardHolderName(resultSet.getString("card_holder_name"))
                .build();

        obj.setId(UUID.fromString(resultSet.getString("id")));

        return Optional.of(obj);
    }
}