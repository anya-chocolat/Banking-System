package com.ironhack.BankingSystem.entities.accounts;

import com.ironhack.BankingSystem.embeddables.Money;
import com.ironhack.BankingSystem.entities.users.AccountHolder;
import com.ironhack.BankingSystem.entities.users.User;
import com.ironhack.BankingSystem.enums.Status;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
public abstract class Account {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long number;
    private Money balance;
    @ManyToOne
    @JoinColumn(name = "primary_owner_id")
    private AccountHolder primaryOwner;
    @ManyToOne
    @JoinColumn(name = "secondary_owner_id")
    private AccountHolder secondaryOwner;
    @Embedded
    private final Money penaltyFee = new Money(BigDecimal.valueOf(40));
    private final LocalDate creationDate = LocalDate.now();
    @Enumerated(EnumType.STRING)
    private Status status;

    public Account(Long number, Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Status status) {
        this.number = number;
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.status = status;
    }
}
