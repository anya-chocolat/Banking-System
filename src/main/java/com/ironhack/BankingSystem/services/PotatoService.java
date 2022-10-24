package com.ironhack.BankingSystem.services;

import com.ironhack.BankingSystem.DTOs.*;
import com.ironhack.BankingSystem.embeddables.Money;
import com.ironhack.BankingSystem.entities.Transaction;
import com.ironhack.BankingSystem.entities.accounts.*;
import com.ironhack.BankingSystem.entities.users.AccountHolder;
import com.ironhack.BankingSystem.entities.users.ThirdParty;
import com.ironhack.BankingSystem.repositories.*;
import com.ironhack.BankingSystem.services.interfaces.PotatoServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class PotatoService implements PotatoServiceInterface {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CheckingAccountRepository checkingAccountRepository;
    @Autowired
    StudentCheckingAccountRepository studentCheckingAccountRepository;
    @Autowired
    SavingsAccountRepository savingsAccountRepository;
    @Autowired
    CreditCardRepository creditCardRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    ThirdPartyRepository thirdPartyRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    RoleRepository roleRepository;

    public List<Account> showAccounts() {
        return accountRepository.findAll();
    }

    public Account newCheckingAccount(CheckingAccountDTO checkingAccount) {
        Money balance = new Money(checkingAccount.getBalanceAmount());
        int secretKey = checkingAccount.getSecretKey();
        AccountHolder primaryOwner = accountHolderRepository.findById(checkingAccount.getPrimaryOwnerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        AccountHolder secondaryOwner = null;
        if (checkingAccount.getSecondaryOwnerId() != null && accountHolderRepository.existsById(checkingAccount.getSecondaryOwnerId())) {
            secondaryOwner = accountHolderRepository.findById(checkingAccount.getSecondaryOwnerId()).get();
        }
        int age = Period.between(primaryOwner.getDateOfBirth(), LocalDate.now()).getYears();
        if (age < 18) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Must be 18 years old to be the primary account owner");
        } else if (age >= 18 && age < 24) {
            return studentCheckingAccountRepository.save(new StudentCheckingAccount(balance, secretKey, primaryOwner, secondaryOwner));
        } else {
            return checkingAccountRepository.save(new CheckingAccount(balance, secretKey, primaryOwner, secondaryOwner));
        }
    }

    public SavingsAccount newSavingsAccount(SavingsAccountDTO savingsAccount) {
        Money balance = new Money(savingsAccount.getBalanceAmount());
        int secretKey = savingsAccount.getSecretKey();
        AccountHolder primaryOwner = accountHolderRepository.findById(savingsAccount.getPrimaryOwnerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        AccountHolder secondaryOwner = null;
        if (savingsAccount.getSecondaryOwnerId() != null && accountHolderRepository.existsById(savingsAccount.getSecondaryOwnerId())) {
            secondaryOwner = accountHolderRepository.findById(savingsAccount.getSecondaryOwnerId()).get();
        }
        Money minimumBalance = new Money(savingsAccount.getMinimumBalance());
        BigDecimal interestRate = savingsAccount.getInterestRate();
        return savingsAccountRepository.save(new SavingsAccount(balance, secretKey, primaryOwner, secondaryOwner, minimumBalance, interestRate));
    }

    public CreditCard newCreditCard(CreditCardDTO creditCard) {
        Money balance = new Money(creditCard.getBalanceAmount());
        int secretKey = creditCard.getSecretKey();
        AccountHolder primaryOwner = accountHolderRepository.findById(creditCard.getPrimaryOwnerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        AccountHolder secondaryOwner = null;
        if (creditCard.getSecondaryOwnerId() != null && accountHolderRepository.existsById(creditCard.getSecondaryOwnerId())) {
            secondaryOwner = accountHolderRepository.findById(creditCard.getSecondaryOwnerId()).get();
        }
        BigDecimal interestRate = creditCard.getInterestRate();
        Money creditLimit = new Money(creditCard.getCreditLimitAmount());
        return creditCardRepository.save(new CreditCard(balance, secretKey, primaryOwner, secondaryOwner, creditLimit, interestRate));
    }

    public Money checkBalance(Long id) {
        if (accountRepository.existsById(id)) {
            switch (accountRepository.findById(id).getClass().getName()) {
                case "CheckingAccount":
                    CheckingAccount checkingAccount = checkingAccountRepository.findById(id).get();
                    BigDecimal feeTime = BigDecimal.valueOf(Period.between(checkingAccount.getLastFeeDate(), LocalDate.now()).getMonths());
                    if (feeTime.compareTo(BigDecimal.ZERO) == 1) {
                        modifyBalance(checkingAccount.getNumber(), checkingAccount.getMonthlyMaintenanceFee().getAmount().multiply(feeTime));
                        checkingAccount.setLastFeeDate(LocalDate.now());
                        checkingAccountRepository.save(checkingAccount);
                    }
                    if (checkingAccount.getBalance().getAmount().compareTo(checkingAccount.getMinimumBalance().getAmount()) == -1) {
                        modifyBalance(checkingAccount.getNumber(), checkingAccount.getPenaltyFee().getAmount());
                        checkingAccountRepository.save(checkingAccount);
                    }
                case "SavingsAccount":
                    SavingsAccount savingsAccount = savingsAccountRepository.findById(id).get();
                    BigDecimal interestTime = BigDecimal.valueOf(Period.between(savingsAccount.getLastInterestDate(), LocalDate.now()).getYears());
                    if (interestTime.compareTo(BigDecimal.ZERO) == 1) {
                        modifyBalance(savingsAccount.getNumber(), savingsAccount.getBalance().getAmount().multiply(savingsAccount.getInterestRate()).multiply(interestTime));
                        savingsAccount.setLastInterestDate(LocalDate.now());
                        savingsAccountRepository.save(savingsAccount);
                    }
                    if (savingsAccount.getBalance().getAmount().compareTo(savingsAccount.getMinimumBalance().getAmount()) == -1) {
                        modifyBalance(savingsAccount.getNumber(), savingsAccount.getPenaltyFee().getAmount());
                        savingsAccountRepository.save(savingsAccount);
                    }
                case "CreditCard":
                    CreditCard creditCard = creditCardRepository.findById(id).get();
                    interestTime = BigDecimal.valueOf(Period.between(creditCard.getLastInterestDate(), LocalDate.now()).getMonths());
                    if (interestTime.compareTo(BigDecimal.ZERO) == 1) {
                        modifyBalance(creditCard.getNumber(), creditCard.getBalance().getAmount().multiply(creditCard.getInterestRate()).multiply(interestTime));
                        creditCard.setLastInterestDate(LocalDate.now());
                        creditCardRepository.save(creditCard);
                    }
                    if (creditCard.getBalance().getAmount().compareTo((creditCard.getCreditLimit().getAmount().negate())) == -1) {
                        modifyBalance(creditCard.getNumber(), creditCard.getPenaltyFee().getAmount());
                        creditCardRepository.save(creditCard);
                    }
            }
            return accountRepository.findById(id).get().getBalance();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void modifyBalance(Long id, BigDecimal amount) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        account.setBalance(new Money(account.getBalance().decreaseAmount(amount)));
        accountRepository.save(account);
    }

    public Transaction transferBetweenAccounts(TransferDTO transfer) {
        String sender = transfer.getSenderName();
        Account origin = accountRepository.findById(transfer.getOriginId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Account destination = accountRepository.findById(transfer.getDestinationId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Money amount = new Money(transfer.getAmount());
        if (!sender.equals(origin.getPrimaryOwner().getName()) && !sender.equals(origin.getSecondaryOwner().getName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        checkBalance(origin.getNumber());
        checkBalance(destination.getNumber());
        if (origin.getBalance().getAmount().compareTo(amount.getAmount()) == -1){
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Insufficient funds!");
        }
        modifyBalance(origin.getNumber(), amount.getAmount());
        modifyBalance(destination.getNumber(), amount.getAmount().negate());
        checkBalance(origin.getNumber());
        checkBalance(destination.getNumber());
        return transactionRepository.save(new Transaction(sender, origin, destination, amount));
    }

    public Transaction transferToThirdParty(ThirdPartyTransferDTO transfer, String hashedKey){
        ThirdParty thirdParty = thirdPartyRepository.findById(transfer.getThirdPartyId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!hashedKey.equals(thirdParty.getHashedKey())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        Account account = accountRepository.findById(transfer.getAccountId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (transfer.getSecretKey() == account.getSecretKey()){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        Money amount = new Money(transfer.getAmount());
        if (account.getBalance().getAmount().compareTo(amount.getAmount()) == -1){
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Insufficient funds!");
        }
        Transaction transaction = new Transaction(amount);
        transaction.setOrigin(account);
        modifyBalance(account.getNumber(), amount.getAmount());
        return transactionRepository.save(transaction);
    }

    public Transaction transferFromThirdParty(ThirdPartyTransferDTO transfer, String hashedKey){
        ThirdParty thirdParty = thirdPartyRepository.findById(transfer.getThirdPartyId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!hashedKey.equals(thirdParty.getHashedKey())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        Account account = accountRepository.findById(transfer.getAccountId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (transfer.getSecretKey() == account.getSecretKey()){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        Money amount = new Money(transfer.getAmount());
        Transaction transaction = new Transaction(amount);
        transaction.setDestination(account);
        modifyBalance(account.getNumber(), amount.getAmount());
        return transactionRepository.save(transaction);
    }

}
