package com.ironhack.BankingSystem.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * A DTO for creating an Account Holder
 */

@Getter
@Setter
@AllArgsConstructor
public class AccountHolderDTO {
    @NotEmpty
    @Column(unique = true)
    private String username;
    @NotEmpty
    @Column(nullable = false)
    private String password;
    @NotEmpty
    private String name;
    @DateTimeFormat(pattern = "dd/MM/yyy")
    private LocalDate dateOfBirth;
    @NotEmpty
    private String streetAddress;
    @NotEmpty
    private String city;
    @Nullable
    @Size(max = 12)
    private String postalCode;
    @Nullable
    private String provinceState;
    @NotEmpty
    private String country;
    @Nullable
    private String mailStreetAddress;
    @Nullable
    private String mailCity;
    @Nullable
    @Size(max = 12)
    private String mailPostalCode;
    @Nullable
    private String mailProvinceState;
    @Nullable
    private String mailCountry;

}