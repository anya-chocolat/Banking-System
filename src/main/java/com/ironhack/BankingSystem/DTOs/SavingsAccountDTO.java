package com.ironhack.BankingSystem.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * A DTO for creating a Savings Account
 */
@Data
@Getter
@Setter
@AllArgsConstructor
public class SavingsAccountDTO{
    @DecimalMin(value = "100")
    private final BigDecimal balanceAmount;
    @NotNull
    private final int secretKey;
    @NotEmpty
    private final Long primaryOwnerId;
    @Nullable
    private final Long secondaryOwnerId;
    @DecimalMax(value = "1000")
    @DecimalMin(value = "100")
    private final BigDecimal minimumBalance;
    @DecimalMax(value = "0.5")
    private final BigDecimal interestRate;
}