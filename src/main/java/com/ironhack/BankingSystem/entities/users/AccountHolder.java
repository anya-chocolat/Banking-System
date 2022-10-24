package com.ironhack.BankingSystem.entities.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.BankingSystem.embeddables.Address;
import com.ironhack.BankingSystem.entities.accounts.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AccountHolder extends User {
    @DateTimeFormat(pattern = "dd/MM/yyy")
    private LocalDate dateOfBirth;
    @Embedded
    private Address primaryAddress;
    @Embedded
    @Nullable
    @AttributeOverrides({
            @AttributeOverride(name = "streetAddress", column = @Column(name = "mail_street_address")),
            @AttributeOverride(name = "city", column = @Column(name = "mail_city")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "mail_postal_code")),
            @AttributeOverride(name = "provinceState", column = @Column(name = "mail_province_state")),
            @AttributeOverride(name = "country", column = @Column(name = "mail_country"))
    })
    private Address mailingAddress;
    @OneToMany(mappedBy = "primaryOwner", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Account> accounts;
    @OneToMany(mappedBy = "secondaryOwner", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Account> secondaryAccounts;

    public AccountHolder(String username, String password, String name, LocalDate dateOfBirth, Address primaryAddress, Address mailingAddress) {
        super(username, password, name);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
    }
}
