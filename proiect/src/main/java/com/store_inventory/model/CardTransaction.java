package com.store_inventory.model;

import com.store_inventory.model.abstracts.Transaction;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.function.BiPredicate;


@Getter
@Setter
@ToString
@SuperBuilder
public class CardTransaction extends Transaction {

    private String cardNumber;
    private String cardHolderName;
    private LocalDate cardExpirationDate;
    private final BiPredicate<String, LocalDate> validateCardDetails = (cn, ce) -> cn.length() == 16 && ce.isAfter(LocalDate.now());

    @Builder
    public CardTransaction(Float amount, String cardNumber, String cardHolderName, LocalDate cardExpirationDate) {
        super(amount);
        if (!validateCardDetails.test(cardNumber, cardExpirationDate)) {
            System.err.println("Card details invalid");
        }
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.cardExpirationDate = cardExpirationDate;
    }
}