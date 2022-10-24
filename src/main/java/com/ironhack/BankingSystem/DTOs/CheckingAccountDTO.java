package com.ironhack.BankingSystem.DTOs;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

/**
 * A DTO for creating a Checking Account
 */
@Data
@Getter
@Setter
@AllArgsConstructor
public class CheckingAccountDTO {
    @DecimalMin(value = "250")
    private BigDecimal balanceAmount;
    @NotNull
    @Digits(integer = 4, fraction = 0)
    private int secretKey;
    @NotNull
    private Long primaryOwnerId;
    @Nullable
    private Long secondaryOwnerId;
}