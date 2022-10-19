package com.ironhack.BankingSystem;

import com.ironhack.BankingSystem.entities.accounts.Account;
import com.ironhack.BankingSystem.entities.accounts.CheckingAccount;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingSystemApplication.class, args);
		Account acc = new CheckingAccount();
		System.out.println(acc.toString());
	}

}
