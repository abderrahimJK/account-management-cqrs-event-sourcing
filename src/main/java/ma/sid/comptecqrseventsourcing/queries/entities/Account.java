package ma.sid.comptecqrseventsourcing.queries.entities;

import lombok.Data;
import ma.sid.comptecqrseventsourcing.commonapi.enums.AccountStatus;

import javax.persistence.*;
import java.util.Collection;

@Entity @Data
public class Account {

    @Id
    private String id;
    private double balance;
    private String currency;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @OneToMany(mappedBy = "account")
    private Collection<Operation> operations;
}
