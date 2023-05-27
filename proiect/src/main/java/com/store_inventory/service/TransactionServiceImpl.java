package com.store_inventory.service;

import com.store_inventory.exceptions.ObjectNotFoundException;
import com.store_inventory.model.CardTransaction;
import com.store_inventory.model.CashTransaction;
import com.store_inventory.model.abstracts.Transaction;
import com.store_inventory.repository.CardTransactionRepository;
import com.store_inventory.repository.CardTransactionRepositoryImpl;
import com.store_inventory.repository.CashTransactionRepository;
import com.store_inventory.repository.CashTransactionRepositoryImpl;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public final class TransactionServiceImpl implements TransactionService{

    private static final CardTransactionRepository cardRepository = new CardTransactionRepositoryImpl();
    private static final CashTransactionRepository cashRepository = new CashTransactionRepositoryImpl();

    public void addTransaction(Transaction T) {
        try {
            if (T.getId() == null) {
                T.setId(UUID.randomUUID());
            }
            if (T instanceof CashTransaction) {
                cashRepository.addNewObject((CashTransaction) T);
            } else {
                cardRepository.addNewObject((CardTransaction) T);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Transaction getTransactionById(UUID transactionId) {
        try {
            Optional<CardTransaction> objectById = cardRepository.getObjectById(transactionId);
            if (objectById.isPresent())
                return objectById.get();
            else {
                Optional<CashTransaction> objectById2 = cashRepository.getObjectById(transactionId);
                if (objectById2.isPresent())
                    return objectById2.get();
            }
            return null;
        } catch (SQLException | ObjectNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
