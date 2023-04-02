package com.store_inventory.model;

import com.store_inventory.model.abstracts.Transaction;
import lombok.*;

@Getter
@Setter
public class CashTransaction extends Transaction {
    @Builder
    CashTransaction(Float amount){
        super(amount);
    }

}
