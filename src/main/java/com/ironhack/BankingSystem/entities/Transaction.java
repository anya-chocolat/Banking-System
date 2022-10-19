package com.ironhack.BankingSystem.entities;

import com.ironhack.BankingSystem.embeddables.Money;
import com.ironhack.BankingSystem.entities.accounts.Account;
import com.ironhack.BankingSystem.entities.users.User;
import com.ironhack.BankingSystem.enums.TransactionType;

import java.time.LocalDateTime;

public class Transaction {
    TransactionType transactionType;
    User theOneWhoPerforms;
    Account origin;
    Account destination;
    Money amount;
    LocalDateTime timeOfTransaction;

}
