package com.ironhack.BankingSystem.entities.accounts;

import com.ironhack.BankingSystem.embeddables.Money;
import com.ironhack.BankingSystem.entities.users.AccountHolder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CreditCard extends Account{
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "credit_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "credit_amount"))
    })
    private Money creditLimit;
    private BigDecimal interestRate = BigDecimal.valueOf(0.0025);
    private LocalDate lastInterestDate = LocalDate.now();

    public CreditCard(Money balance, int secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money creditLimit, BigDecimal interestRate) {
        super(balance, secretKey, primaryOwner, secondaryOwner);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }
}
