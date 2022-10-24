package com.ironhack.BankingSystem.services;

import com.ironhack.BankingSystem.DTOs.AccountHolderDTO;
import com.ironhack.BankingSystem.DTOs.AdminDTO;
import com.ironhack.BankingSystem.DTOs.ThirdPartyDTO;
import com.ironhack.BankingSystem.embeddables.Address;
import com.ironhack.BankingSystem.entities.users.*;
import com.ironhack.BankingSystem.repositories.*;
import com.ironhack.BankingSystem.services.interfaces.PotatoUserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PotatoUserService implements PotatoUserServiceInterface {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    ThirdPartyRepository thirdPartyRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public Admin addAdmin(AdminDTO admin) {
        String username = admin.getUsername();
        String password = passwordEncoder.encode(admin.getPassword());
        String name = admin.getName();
        Admin newAdmin = adminRepository.save(new Admin(username, password, name));
        roleRepository.save(new Role("ADMIN", newAdmin));
        return newAdmin;
    }

    public AccountHolder addAccountHolder(AccountHolderDTO accountHolder) {
        String username = accountHolder.getUsername();
        String password = passwordEncoder.encode(accountHolder.getPassword());
        String name = accountHolder.getName();
        LocalDate dateOfBirth = accountHolder.getDateOfBirth();
        String streetAddress = accountHolder.getStreetAddress();
        String city = accountHolder.getCity();
        String postalCode = accountHolder.getPostalCode();
        String provinceState = accountHolder.getProvinceState();
        String country = accountHolder.getCountry();
        Address address = new Address(streetAddress, city, postalCode, provinceState, country);
        Address mailingAddress = null;
        if (accountHolder.getMailStreetAddress() != null || !accountHolder.getMailStreetAddress().isEmpty()){
            String mailStreetAddress = accountHolder.getMailStreetAddress();
            String mailCity = accountHolder.getMailCity();
            String mailPostalCode = accountHolder.getMailPostalCode();
            String mailProvinceState = accountHolder.getMailProvinceState();
            String mailCountry = accountHolder.getMailCountry();
            mailingAddress = new Address(mailStreetAddress, mailCity, mailPostalCode, mailProvinceState, mailCountry);
        }
        AccountHolder newAccountHolder = accountHolderRepository.save(new AccountHolder(username, password, name, dateOfBirth, address, mailingAddress));
        roleRepository.save(new Role("CLIENT", newAccountHolder));
        return newAccountHolder;
    }

    public ThirdParty addThirdParty(ThirdPartyDTO thirdParty) {
        String name = thirdParty.getName();
        String hashedKey = thirdParty.getHashedKey();
        ThirdParty newThirdParty = thirdPartyRepository.save(new ThirdParty(name, hashedKey));
        roleRepository.save(new Role("THIRD_PARTY", newThirdParty));
        return newThirdParty;
    }

    @Override
    public List<User> showUsers(UserDetails userDetails) {
        return userRepository.findAll();
    }

}
