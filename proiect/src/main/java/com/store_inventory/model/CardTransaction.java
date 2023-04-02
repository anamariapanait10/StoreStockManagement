package com.store_inventory.model;

import com.store_inventory.model.abstracts.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.function.BiPredicate;
import java.util.function.Predicate;


@Getter
@Setter
@ToString
public class CardTransaction extends Transaction {

    private String cardNumber;
    private String cardHolderName;
    private LocalDate cardExpirationDate;
    private final BiPredicate<String, LocalDate> validateCardDetails = (cn, ce) -> cn.length() == 16 && ce.isAfter(LocalDate.now());

    public CardTransaction(String cardNumber, String cardHolderName, LocalDate cardExpirationDate) {
        if (!validateCardDetails.test(cardNumber, cardExpirationDate)) {
            System.err.println("Card details invalid");
        }
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.cardExpirationDate = cardExpirationDate;
    }
}
