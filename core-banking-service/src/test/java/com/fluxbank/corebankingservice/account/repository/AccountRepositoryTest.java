package com.fluxbank.corebankingservice.account.repository;

import com.fluxbank.corebankingservice.account.entity.Account;
import com.fluxbank.corebankingservice.user.entity.User;
import com.fluxbank.corebankingservice.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindAccountsByUserId() {

        User user = User.builder()
                .name("John")
                .email("john@test.com")
                .phone("1234567890")
                .build();

        user = userRepository.save(user);

        Account account = Account.builder()
                .accountNumber("ACC123")
                .accountType("SAVINGS")
                .balance(new BigDecimal("1000"))
                .status("ACTIVE")
                .user(user)
                .build();

        accountRepository.save(account);

        List<Account> accounts = accountRepository.findByUserId(user.getId());

        assertEquals(1, accounts.size());
        assertEquals("SAVINGS", accounts.get(0).getAccountType());
    }

    @Test
    void shouldReturnEmptyWhenUserHasNoAccounts() {

        List<Account> accounts = accountRepository.findByUserId(999L);

        assertTrue(accounts.isEmpty());
    }

    @Test
    void shouldSaveAccount() {

        User user = User.builder()
                .name("Jane")
                .email("jane@test.com")
                .phone("0000000000")
                .build();

        user = userRepository.save(user);

        Account account = Account.builder()
                .accountNumber("ACC999")
                .accountType("CURRENT")
                .balance(new BigDecimal("5000"))
                .status("ACTIVE")
                .user(user)
                .build();

        Account saved = accountRepository.save(account);

        assertNotNull(saved.getId());
    }
}