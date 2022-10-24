package com.ironhack.BankingSystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.BankingSystem.embeddables.Address;
import com.ironhack.BankingSystem.embeddables.Money;
import com.ironhack.BankingSystem.entities.accounts.CheckingAccount;
import com.ironhack.BankingSystem.entities.accounts.CreditCard;
import com.ironhack.BankingSystem.entities.accounts.SavingsAccount;
import com.ironhack.BankingSystem.entities.accounts.StudentCheckingAccount;
import com.ironhack.BankingSystem.entities.users.AccountHolder;
import com.ironhack.BankingSystem.entities.users.Admin;
import com.ironhack.BankingSystem.entities.users.Role;
import com.ironhack.BankingSystem.repositories.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class PotatoTests {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CheckingAccountRepository checkingAccountRepository;
    @Autowired
    StudentCheckingAccountRepository studentCheckingAccountRepository;
    @Autowired
    SavingsAccountRepository savingsAccountRepository;
    @Autowired
    CreditCardRepository creditCardRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    ThirdPartyRepository thirdPartyRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    PasswordEncoder passwordEncoder;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Admin admin = new Admin("Toto", passwordEncoder.encode("MercedesF1"), "Torger Christian Wolff");
        roleRepository.save(new Role("ADMIN", admin));

        Address montmelo = new Address("Circuit de Barcelona-Catalunya", "Montmeló", "08160", "Barcelona", "Spain");
        Address monza = new Address("Autodromo Nazionale di Monza", "Monza", "20900", "Province of Monza and Brianza", "Italy");

        AccountHolder fernando = accountHolderRepository.save(new AccountHolder("Fernando", passwordEncoder.encode("1234"), "Fernando Alonso Díaz", LocalDate.of(1981, 7, 29), monza, montmelo));
        AccountHolder carlos = accountHolderRepository.save(new AccountHolder("SainzJr", passwordEncoder.encode("Ferrari"), "Carlos Sainz", LocalDate.of(1994, 9, 1), montmelo, montmelo));
        AccountHolder sainz = accountHolderRepository.save(new AccountHolder("Sainz", passwordEncoder.encode("Rally"), "Carlos Sainz", LocalDate.of(1962, 4, 12), montmelo, montmelo));
        AccountHolder lando = accountHolderRepository.save(new AccountHolder("Lando", passwordEncoder.encode("LandoUK"), "Lando Norris", LocalDate.of(1999, 11, 13), monza, null));
        AccountHolder max = accountHolderRepository.save(new AccountHolder("Max", passwordEncoder.encode("RedBull"), "Max Emilian Verstappen", LocalDate.of(1997, 9, 30), montmelo, null));
        roleRepository.save(new Role("CLIENT", fernando));
        roleRepository.save(new Role("CLIENT", carlos));
        roleRepository.save(new Role("CLIENT", sainz));
        roleRepository.save(new Role("CLIENT", lando));
        roleRepository.save(new Role("CLIENT", max));

        CheckingAccount fernandoChecking = checkingAccountRepository.save(new CheckingAccount(new Money(BigDecimal.valueOf(28000)), 1981, fernando, null));
        StudentCheckingAccount landoChecking = studentCheckingAccountRepository.save(new StudentCheckingAccount(new Money(BigDecimal.valueOf(6900)), 1999, lando, null));
        SavingsAccount sainzSavings = savingsAccountRepository.save(new SavingsAccount(new Money(BigDecimal.valueOf(100500)), 1234, sainz, carlos, new Money(BigDecimal.valueOf(1000)), BigDecimal.valueOf(0.25)));
        CreditCard creditCard = creditCardRepository.save(new CreditCard(new Money(BigDecimal.valueOf(420420)), 0000, max, null, new Money(BigDecimal.valueOf(99999)), BigDecimal.valueOf(0.2)));
    }

    @AfterEach
    void tearDown() {
        roleRepository.deleteAll();
        adminRepository.deleteAll();
        accountHolderRepository.deleteAll();
        thirdPartyRepository.deleteAll();
        checkingAccountRepository.deleteAll();
        studentCheckingAccountRepository.deleteAll();
        savingsAccountRepository.deleteAll();
        creditCardRepository.deleteAll();
        transactionRepository.deleteAll();
        userRepository.deleteAll();
        accountRepository.deleteAll();
    }

//    @Test
//    @WithMockUser("sainz")
//    void checkBalance_works() throws Exception {
//        MvcResult mvcResult = mockMvc.perform(get("/check-balance").param("id", "3").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isFound()).andReturn();
//        assertEquals(BigDecimal.valueOf(100500), accountRepository.findById(3L).get().getBalance().getAmount());
//    }


}
