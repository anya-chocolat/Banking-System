package com.ironhack.BankingSystem.DTOs;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * A DTO for the making a transfer to/from Third Party
 */

@Getter
@Setter
@NoArgsConstructor
public class ThirdPartyTransferDTO {
    @NotNull
    private Long thirdPartyId;
    private String hashedKey;
    private Long accountId;
    private int secretKey;
    private BigDecimal amount;
}