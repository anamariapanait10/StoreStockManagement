package net.pao.laboratorpao.model.abstracts;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "disable_date")
    private LocalDate disableDate;

    @Column(name = "updated_date")
    private LocalDate updatedDate;
}
