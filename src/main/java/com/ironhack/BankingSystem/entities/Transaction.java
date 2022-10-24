package com.ironhack.BankingSystem.entities;

import com.ironhack.BankingSystem.embeddables.Money;
import com.ironhack.BankingSystem.entities.accounts.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String senderName;
    @ManyToOne
    @JoinColumn(name = "origin_account")
    private Account origin;
    @ManyToOne
    @JoinColumn(name = "destination_account")
    private Account destination;
    @Embedded
    private Money amount;
    private final LocalDateTime timeOfTransaction = LocalDateTime.now();

    // Constructor for a transaction between two accounts:
    public Transaction(String senderName, Account origin, Account destination, Money amount) {
        this.senderName = senderName;
        this.origin = origin;
        this.destination = destination;
        this.amount = amount;
    }

    // Constructor for a transaction to/from a Third party
    public Transaction(Money amount) {
        this.amount = amount;
    }
}
