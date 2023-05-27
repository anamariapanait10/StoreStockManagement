package com.store_inventory.service;

import com.store_inventory.model.abstracts.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public final class TransactionServiceImpl implements TransactionService{

    private List<Transaction> transactionList = new ArrayList<>();

    public void addTransaction(Transaction T){
        transactionList.add(T);
    }
    public Transaction getTransactionById(UUID transactionId) {
        return transactionList.stream().filter(transaction -> transaction.getId() == transactionId).findAny().get();
    }

}
