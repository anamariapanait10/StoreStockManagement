package com.store_inventory.repository;

import com.store_inventory.model.CardTransaction;

import java.sql.SQLException;
import java.util.Optional;

public sealed interface CardTransactionRepository extends Repository<CardTransaction> permits CardTransactionRepositoryImpl{
    Optional<CardTransaction> getObjectByName (String name) throws SQLException;
}
