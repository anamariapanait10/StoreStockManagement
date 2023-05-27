package com.store_inventory.model.abstracts;

import com.store_inventory.model.abstracts.AbstractEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
public abstract class Transaction extends AbstractEntity {
    private float amount;
}
