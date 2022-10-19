package com.ironhack.BankingSystem.entities.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.BankingSystem.embeddables.Address;
import com.ironhack.BankingSystem.entities.accounts.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AccountHolder extends User{
    @OneToMany(mappedBy = "primaryOwner")
    @JsonIgnore
    private List<Account> accounts;
    @Embedded
    private Address primaryAddress;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name= "street", column = @Column(name = "mail_street")),
            @AttributeOverride(name = "city", column = @Column(name = "mail_city")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "mail_postal_code")),
            @AttributeOverride(name = "provinceState", column = @Column(name = "mail_province_state")),
            @AttributeOverride(name = "country", column = @Column(name = "mail_country"))
    })
    private Address mailingAdddress;

    public AccountHolder(Address primaryAddress, Address mailingAdddress) {
        this.primaryAddress = primaryAddress;
        this.mailingAdddress = mailingAdddress;
    }

    public AccountHolder(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
        this.mailingAdddress = null;
    }
}
