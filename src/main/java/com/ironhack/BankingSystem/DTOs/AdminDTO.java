package com.ironhack.BankingSystem.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

/**
 * A DTO for new Admin creation
 */

@Getter
@Setter
@AllArgsConstructor
public class AdminDTO {
    @NotEmpty
    @Column(unique = true)
    private String username;
    @NotEmpty
    @Column(nullable = false)
    private String password;
    @NotEmpty
    private String name;
}