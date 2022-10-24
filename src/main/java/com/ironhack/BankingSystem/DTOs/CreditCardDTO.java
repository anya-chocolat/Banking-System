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
 * A DTO for creating a new Credit Card
 */
@Data
@Getter
@Setter
@AllArgsConstructor
public class CreditCardDTO{
    @NotNull
    private final BigDecimal balanceAmount;
    @NotEmpty
    private final int secretKey;
    @NotNull
    private final Long primaryOwnerId;
    @Nullable
    private final Long secondaryOwnerId;
    @DecimalMax(value = "100000")
    @DecimalMin(value = "100")
    private final BigDecimal creditLimitAmount;
    @DecimalMax(value = "0.2")
    @DecimalMin(value = "0.1")
    private final BigDecimal interestRate;
}