package net.pao.laboratorpao.model;

import jakarta.persistence.*;
import lombok.*;
import net.pao.laboratorpao.model.abstracts.AbstractEntity;
import net.pao.laboratorpao.model.enums.ClientType;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "clients")
public class Client extends AbstractEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "year_of_birth")
    private Integer yearOfBirth;

    @Column(name = "place_of_birth")
    private String placeOfBirth;

    @Column(name = "phone_number")
    private Long phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "client_type")
    @Enumerated(value = EnumType.STRING)
    private ClientType clientType;

    @OneToMany
    @JoinColumn(name = "accounts")
    private List<Account> accounts;
}