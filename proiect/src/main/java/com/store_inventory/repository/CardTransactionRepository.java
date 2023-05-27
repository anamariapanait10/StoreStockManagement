package com.store_inventory.repository;

import com.store_inventory.model.CardTransaction;

public sealed interface CardTransactionRepository extends Repository<CardTransaction> permits CardTransactionRepositoryImpl{
//    Optional<CardTransaction> getCardTransactionsByDate(String date) throws SQLException;
}
