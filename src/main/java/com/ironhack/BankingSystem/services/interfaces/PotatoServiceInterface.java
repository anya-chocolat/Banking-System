package com.ironhack.BankingSystem.services.interfaces;

import com.ironhack.BankingSystem.DTOs.*;
import com.ironhack.BankingSystem.embeddables.Money;
import com.ironhack.BankingSystem.entities.Transaction;
import com.ironhack.BankingSystem.entities.accounts.Account;
import com.ironhack.BankingSystem.entities.accounts.CreditCard;
import com.ironhack.BankingSystem.entities.accounts.SavingsAccount;

import java.math.BigDecimal;
import java.util.List;

public interface PotatoServiceInterface {
    List<Account> showAccounts();
    Account newCheckingAccount(CheckingAccountDTO checkingAccount);
    SavingsAccount newSavingsAccount(SavingsAccountDTO savingsAccount);
    CreditCard newCreditCard(CreditCardDTO creditCard);
    Money checkBalance(Long id);
    void modifyBalance(Long id, BigDecimal amount);
    Transaction transferBetweenAccounts(TransferDTO transfer);
    Transaction transferToThirdParty(ThirdPartyTransferDTO transfer, String hashedKey);
    Transaction transferFromThirdParty(ThirdPartyTransferDTO transfer, String hashedKey);
}
