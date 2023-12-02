package ma.sdia.comptecqrses.query.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.sdia.comptecqrses.commonApi.enums.AccountStatus;

import java.util.Collection;

@Entity @AllArgsConstructor @NoArgsConstructor @Data @Builder

public class Account {
    @Id
    private String id;
    private double balance;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private String currency;
    @OneToMany(mappedBy = "account")
    private Collection<Operation> operations;

}
