package com.fluxbank.corebankingservice.transaction.repository;

import com.fluxbank.corebankingservice.account.entity.Account;
import com.fluxbank.corebankingservice.account.repository.AccountRepository;
import com.fluxbank.corebankingservice.transaction.entity.Transaction;
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
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    private Account createAccount() {

        User user = userRepository.save(
                User.builder()
                        .name("Test User")
                        .email("test@mail.com")
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
    void shouldSaveTransaction() {

        Account account = createAccount();

        Transaction tx = Transaction.builder()
                .transactionReference("TXN-1")
                .transactionType("DEPOSIT")
                .amount(BigDecimal.valueOf(500))
                .account(account)
                .transactionTime(LocalDateTime.now())
                .status("SUCCESS")
                .direction("CREDIT")
                .build();

        Transaction saved = transactionRepository.save(tx);

        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void shouldFindTransactionsByAccountIdOrdered() {

        Account account = createAccount();

        Transaction tx1 = Transaction.builder()
                .transactionReference("TXN-1")
                .transactionType("DEPOSIT")
                .amount(BigDecimal.valueOf(500))
                .account(account)
                .transactionTime(LocalDateTime.now().minusMinutes(10))
                .status("SUCCESS")
                .direction("CREDIT")
                .build();

        Transaction tx2 = Transaction.builder()
                .transactionReference("TXN-2")
                .transactionType("WITHDRAW")
                .amount(BigDecimal.valueOf(200))
                .account(account)
                .transactionTime(LocalDateTime.now())
                .status("SUCCESS")
                .direction("DEBIT")
                .build();

        transactionRepository.save(tx1);
        transactionRepository.save(tx2);

        List<Transaction> transactions =
                transactionRepository.findByAccountIdOrderByTransactionTimeDesc(account.getId());

        assertThat(transactions).hasSize(2);
        assertThat(transactions.get(0).getTransactionReference()).isEqualTo("TXN-2");
    }

    @Test
    void shouldFindAllTransactions() {

        Account account = createAccount();

        Transaction tx = Transaction.builder()
                .transactionReference("TXN-3")
                .transactionType("DEPOSIT")
                .amount(BigDecimal.valueOf(300))
                .account(account)
                .transactionTime(LocalDateTime.now())
                .status("SUCCESS")
                .direction("CREDIT")
                .build();

        transactionRepository.save(tx);

        List<Transaction> transactions = transactionRepository.findAll();

        assertThat(transactions).isNotEmpty();
    }
}