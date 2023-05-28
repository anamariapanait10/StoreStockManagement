package net.pao.laboratorpao.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.pao.laboratorpao.model.abstracts.AbstractEntity;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account extends AbstractEntity {

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name="alias")
    private String alias;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name="account_number")
    private BigDecimal accountNumber;
}