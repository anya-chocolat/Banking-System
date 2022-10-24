package com.ironhack.BankingSystem.controllers;

import com.ironhack.BankingSystem.DTOs.*;
import com.ironhack.BankingSystem.embeddables.Money;
import com.ironhack.BankingSystem.entities.Transaction;
import com.ironhack.BankingSystem.entities.accounts.Account;
import com.ironhack.BankingSystem.entities.accounts.CreditCard;
import com.ironhack.BankingSystem.entities.accounts.SavingsAccount;
import com.ironhack.BankingSystem.entities.users.AccountHolder;
import com.ironhack.BankingSystem.entities.users.Admin;
import com.ironhack.BankingSystem.entities.users.ThirdParty;
import com.ironhack.BankingSystem.entities.users.User;
import com.ironhack.BankingSystem.services.interfaces.PotatoServiceInterface;
import com.ironhack.BankingSystem.services.interfaces.PotatoUserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class PotatoController {
    @Autowired
    PotatoServiceInterface potatoService;
    @Autowired
    PotatoUserServiceInterface potatoUserService;

    @GetMapping("/users")
    public List<User> showUsers(@AuthenticationPrincipal UserDetails userDetails) {
        return potatoUserService.showUsers(userDetails);
    }

    @PostMapping("/users/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public Admin addAdmin(@RequestBody AdminDTO admin) {
        return potatoUserService.addAdmin(admin);
    }

    @PostMapping("/users/client")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder addAccountHolder(@RequestBody AccountHolderDTO accountHolder) {
        return potatoUserService.addAccountHolder(accountHolder);
    }

    @PostMapping("/users/third-party")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty addThirdParty(@RequestBody ThirdPartyDTO thirdParty) {
        return potatoUserService.addThirdParty(thirdParty);
    }

    @GetMapping("/accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> showAccounts() {
        return potatoService.showAccounts();
    }

    @PostMapping("/accounts/checking")
    @ResponseStatus(HttpStatus.CREATED)
    public Account newCheckingAccount(@RequestBody CheckingAccountDTO checkingAccount) {
        return potatoService.newCheckingAccount(checkingAccount);
    }

    @PostMapping("/accounts/savings")
    @ResponseStatus(HttpStatus.CREATED)
    public SavingsAccount newSavingsAccount(@RequestBody SavingsAccountDTO savingsAccountDTO) {
        return potatoService.newSavingsAccount(savingsAccountDTO);
    }

    @PostMapping("/accounts/credit-card")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard newCreditCard(@RequestBody CreditCardDTO creditCardDTO) {
        return potatoService.newCreditCard(creditCardDTO);
    }

    @GetMapping("/check-balance")
    @ResponseStatus(HttpStatus.FOUND)
    public Money checkBalance(@RequestParam Long id) {
        return potatoService.checkBalance(id);
    }

    @PatchMapping("/accounts/modify-balance")
    @ResponseStatus(HttpStatus.OK)
    public void modifyBalance(@RequestParam Long id, @RequestParam BigDecimal amount) {
        potatoService.checkBalance(id);
        potatoService.modifyBalance(id, amount);
        potatoService.checkBalance(id);
    }

    @PatchMapping("/transfer")
    @ResponseStatus(HttpStatus.OK)
    public Transaction transferBetweenAccounts(TransferDTO transfer) {
        return potatoService.transferBetweenAccounts(transfer);
    }

    @PatchMapping("/third-party/to")
    @ResponseStatus(HttpStatus.OK)
    public Transaction transferToThirdParty(@RequestBody ThirdPartyTransferDTO thirdPartyTransferDTO, @RequestHeader String hashedKey) {
        return potatoService.transferToThirdParty(thirdPartyTransferDTO, hashedKey);
    }

    @PatchMapping("/third-party/from")
    @ResponseStatus(HttpStatus.OK)
    public Transaction transferFromThirdParty(@RequestBody ThirdPartyTransferDTO thirdPartyTransferDTO, @RequestHeader String hashedKey) {
        return potatoService.transferFromThirdParty(thirdPartyTransferDTO, hashedKey);
    }

}
