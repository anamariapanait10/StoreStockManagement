package com.store_inventory.model;

import com.store_inventory.model.abstracts.Transaction;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class CashTransaction extends Transaction {
    @Builder
    CashTransaction(Float amount){
        super(amount);
    }

}