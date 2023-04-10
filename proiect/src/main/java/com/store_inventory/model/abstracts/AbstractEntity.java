package com.store_inventory.model.abstracts;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.UUID;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode
public abstract class AbstractEntity {
    private UUID id;
    private LocalDate creationDate;

    public AbstractEntity() {
        id = UUID.randomUUID();
        creationDate = LocalDate.now();
    }
}