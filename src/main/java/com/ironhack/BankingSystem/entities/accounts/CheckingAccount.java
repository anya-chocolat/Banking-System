package com.ironhack.BankingSystem.entities.accounts;

import com.ironhack.BankingSystem.embeddables.Money;
import com.ironhack.BankingSystem.entities.users.AccountHolder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CheckingAccount extends Account {
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "min_balance_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "min_balance_amount"))
    })
    private final Money minimumBalance = new Money(BigDecimal.valueOf(250));
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "maintenance_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "maintenance_amount"))
    })
    private final Money monthlyMaintenanceFee = new Money(BigDecimal.valueOf(12));
    private LocalDate lastFeeDate;

    public CheckingAccount(Money balance, int secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(balance, secretKey, primaryOwner, secondaryOwner);
        this.lastFeeDate = LocalDate.now();
    }
}
