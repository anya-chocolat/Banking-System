package com.ironhack.BankingSystem.entities.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ThirdParty extends User{
    @Column(nullable = false, unique = true)
    private String hashedKey;

    public ThirdParty(String name, String hashedKey) {
        super(null, null, name);
        this.hashedKey = hashedKey;
    }
}
