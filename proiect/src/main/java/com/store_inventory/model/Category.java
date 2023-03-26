package com.store_inventory.model;

import com.store_inventory.model.abstracts.AbstractEntity;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Category extends AbstractEntity {
    private String name;
    private UUID categoryParent;
}
