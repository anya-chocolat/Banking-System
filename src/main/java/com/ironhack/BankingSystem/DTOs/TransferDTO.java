package com.ironhack.BankingSystem.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * A DTO for the making a transfer between accounts
 */

@Getter
@Setter
@AllArgsConstructor
public class TransferDTO {
    private String senderName;
    private long originId;
    private long destinationId;
    private BigDecimal amount;
}
