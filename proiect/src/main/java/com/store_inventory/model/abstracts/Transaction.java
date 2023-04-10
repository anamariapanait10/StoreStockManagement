package com.store_inventory.model.abstracts;

import com.store_inventory.model.abstracts.AbstractEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Transaction extends AbstractEntity {
    private float amount;
}
