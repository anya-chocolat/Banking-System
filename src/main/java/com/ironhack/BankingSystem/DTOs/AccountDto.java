package com.ironhack.BankingSystem.DTOs;

import com.ironhack.BankingSystem.embeddables.Money;
import com.ironhack.BankingSystem.entities.users.AccountHolder;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.ironhack.BankingSystem.entities.accounts.Account} entity
 */
@Data
public class AccountDto implements Serializable {
    private final Money balance;
    private final AccountHolder primaryOwner;
    private final AccountHolder secondaryOwner;
}