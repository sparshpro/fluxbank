package com.fluxbank.corebankingservice.ledger.repository;

import com.fluxbank.corebankingservice.account.entity.Account;
import com.fluxbank.corebankingservice.account.repository.AccountRepository;
import com.fluxbank.corebankingservice.ledger.entity.LedgerEntry;
import com.fluxbank.corebankingservice.user.entity.User;
import com.fluxbank.corebankingservice.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class LedgerRepositoryTest {

    @Autowired
    private LedgerRepository ledgerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    private Account createAccount() {

        User user = userRepository.save(
                User.builder()
                        .name("Test User")
                        .email("user@mail.com")
                        .phone("9999999999")
                        .build()
        );

        return accountRepository.save(
                Account.builder()
                        .accountNumber("ACC1001")
                        .accountType("SAVINGS")
                        .balance(BigDecimal.valueOf(1000))
                        .status("ACTIVE")
                        .user(user)
                        .build()
        );
    }

    @Test
    void shouldSaveLedgerEntry() {

        Account account = createAccount();

        LedgerEntry entry = LedgerEntry.builder()
                .account(account)
                .amount(BigDecimal.valueOf(500))
                .entryType("CREDIT")
                .transactionReference("TXN-1")
                .createdAt(LocalDateTime.now())
                .build();

        LedgerEntry saved = ledgerRepository.save(entry);

        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void shouldFindAllLedgerEntries() {

        Account account = createAccount();

        LedgerEntry entry = LedgerEntry.builder()
                .account(account)
                .amount(BigDecimal.valueOf(200))
                .entryType("DEBIT")
                .transactionReference("TXN-2")
                .createdAt(LocalDateTime.now())
                .build();

        ledgerRepository.save(entry);

        List<LedgerEntry> entries = ledgerRepository.findAll();

        assertThat(entries).isNotEmpty();
    }
}