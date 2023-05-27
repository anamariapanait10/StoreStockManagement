package com.store_inventory.repository;

import com.store_inventory.model.CashTransaction;

public sealed interface CashTransactionRepository extends Repository<CashTransaction> permits CashTransactionRepositoryImpl{
}
