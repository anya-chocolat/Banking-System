package com.ironhack.BankingSystem.entities.accounts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CheckingAccount extends Account{

    @Override
    public void transfer(Account origin, Account destination) {

    }
}
