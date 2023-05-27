package com.store_inventory.service;

import com.store_inventory.model.abstracts.Transaction;

import java.util.UUID;

public sealed interface TransactionService permits TransactionServiceImpl {

    public void addTransaction(Transaction T);
    public Transaction getTransactionById(UUID transactionId);

}
