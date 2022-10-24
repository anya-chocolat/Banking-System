package com.ironhack.BankingSystem.entities.accounts;

import com.ironhack.BankingSystem.embeddables.Money;
import com.ironhack.BankingSystem.entities.users.AccountHolder;
import com.ironhack.BankingSystem.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StudentCheckingAccount extends Account{

    public StudentCheckingAccount(Money balance, int secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(balance, secretKey, primaryOwner, secondaryOwner);
    }
}
