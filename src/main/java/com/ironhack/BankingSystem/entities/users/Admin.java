package com.ironhack.BankingSystem.entities.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Admin extends User{

    public Admin(String username, String password, String name) {
        super(username, password, name);
    }
}
