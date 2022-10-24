package com.ironhack.BankingSystem.services.interfaces;

import com.ironhack.BankingSystem.DTOs.AccountHolderDTO;
import com.ironhack.BankingSystem.DTOs.AdminDTO;
import com.ironhack.BankingSystem.DTOs.ThirdPartyDTO;
import com.ironhack.BankingSystem.entities.users.AccountHolder;
import com.ironhack.BankingSystem.entities.users.Admin;
import com.ironhack.BankingSystem.entities.users.ThirdParty;
import com.ironhack.BankingSystem.entities.users.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface PotatoUserServiceInterface {
    Admin addAdmin(AdminDTO admin);
    AccountHolder addAccountHolder(AccountHolderDTO accountHolder);
    ThirdParty addThirdParty(ThirdPartyDTO thirdParty);
    List<User> showUsers(UserDetails userDetails);
}
