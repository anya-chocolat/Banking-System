package com.ironhack.BankingSystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ironhack.BankingSystem.DTOs.AccountHolderDTO;
import com.ironhack.BankingSystem.DTOs.AdminDTO;
import com.ironhack.BankingSystem.DTOs.TransferDTO;
import com.ironhack.BankingSystem.embeddables.Address;
import com.ironhack.BankingSystem.embeddables.Money;
import com.ironhack.BankingSystem.entities.accounts.CheckingAccount;
import com.ironhack.BankingSystem.entities.accounts.CreditCard;
import com.ironhack.BankingSystem.entities.accounts.SavingsAccount;
import com.ironhack.BankingSystem.entities.accounts.StudentCheckingAccount;
import com.ironhack.BankingSystem.entities.users.AccountHolder;
import com.ironhack.BankingSystem.entities.users.Admin;
import com.ironhack.BankingSystem.entities.users.Role;
import com.ironhack.BankingSystem.entities.users.ThirdParty;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    CheckingAccount fernandoChecking;
    StudentCheckingAccount landoChecking;
    SavingsAccount sainzSavings;
    CreditCard creditCard;
    ThirdParty thirdParty;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Admin toto = adminRepository.save(new Admin("Toto", passwordEncoder.encode("MercedesF1"), "Torger Christian Wolff"));
        roleRepository.save(new Role("ADMIN", toto));

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

        fernandoChecking = checkingAccountRepository.save(new CheckingAccount(new Money(BigDecimal.valueOf(28000)), 1981, fernando, null));
        landoChecking = studentCheckingAccountRepository.save(new StudentCheckingAccount(new Money(BigDecimal.valueOf(6900)), 1999, lando, null));
        sainzSavings = savingsAccountRepository.save(new SavingsAccount(new Money(BigDecimal.valueOf(100500)), 1234, sainz, carlos, new Money(BigDecimal.valueOf(1000)), BigDecimal.valueOf(0.25)));
        creditCard = creditCardRepository.save(new CreditCard(new Money(BigDecimal.valueOf(420420)), 0000, max, null, new Money(BigDecimal.valueOf(99999)), BigDecimal.valueOf(0.2)));
        thirdParty = thirdPartyRepository.save(new ThirdParty("Ferrari", "1234"));
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

    @Test
    @WithMockUser("toto")
    void showUsers_works() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/users")).andExpect(status().isOk()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Fernando Alonso"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Carlos Sainz"));
    }

    @Test
    @WithMockUser("toto")
    void addAdmin_works() throws Exception {
        AdminDTO adminDTO = new AdminDTO("guenther", "Haas1965", "Guenther Steiner");
        String body = objectMapper.writeValueAsString(adminDTO);
        MvcResult mvcResult = mockMvc.perform(post("/users/admin").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Guenther Steiner"));
    }

    @Test
    @WithMockUser("fernando")
    void transfer_works() throws Exception {
        TransferDTO transferDTO = new TransferDTO(fernandoChecking.getPrimaryOwner().getName(), fernandoChecking.getNumber(), landoChecking.getNumber(), BigDecimal.valueOf(8000));
        String body = objectMapper.writeValueAsString(transferDTO);
        MvcResult mvcResult = mockMvc.perform(patch("/transfer").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Fernando Alonso"));
        assertTrue(fernandoChecking.getBalance().getAmount().toString().contains("20000"));
    }

//    @Test
//    @WithMockUser("toto")
//    void addAccountHolder_works() throws Exception {
//        AccountHolderDTO accountHolderDTO = new AccountHolderDTO("SainzJr", "Ferrari", "Carlos Sainz", LocalDate.of(1994, 9, 1), "Circuit de Barcelona-Catalunya", "Montmeló", "08160", "Barcelona", "Spain", "Autodromo Nazionale di Monza", "Monza", "20900", "Province of Monza and Brianza", "Italy");
//        String body = objectMapper.writeValueAsString(accountHolderDTO);
//        MvcResult mvcResult = mockMvc.perform(post("/users/client").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
//        assertTrue(mvcResult.getResponse().getContentAsString().contains("Carlos Sainz"));
//        //assertTrue(mvcResult.getResponse().getContentAsString().contains("Circuit de Barcelona-Catalunya"));
//    }


    @Test
    @WithMockUser("sainz")
    void checkBalance_works() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/check-balance").param("id", sainzSavings.getNumber().toString()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isFound()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("100500"));
    }

}
