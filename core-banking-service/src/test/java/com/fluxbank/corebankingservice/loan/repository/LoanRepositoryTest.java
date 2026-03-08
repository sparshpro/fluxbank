package com.fluxbank.corebankingservice.loan.repository;

import com.fluxbank.corebankingservice.account.entity.Account;
import com.fluxbank.corebankingservice.account.repository.AccountRepository;
import com.fluxbank.corebankingservice.loan.entity.Loan;
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
class LoanRepositoryTest {

    @Autowired
    private LoanRepository loanRepository;

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
    void shouldSaveLoan() {

        Account account = createAccount();

        Loan loan = Loan.builder()
                .account(account)
                .principalAmount(BigDecimal.valueOf(10000))
                .interestRate(BigDecimal.valueOf(10))
                .tenureMonths(12)
                .emiAmount(BigDecimal.valueOf(879))
                .status("APPLIED")
                .createdAt(LocalDateTime.now())
                .build();

        Loan savedLoan = loanRepository.save(loan);

        assertThat(savedLoan.getId()).isNotNull();
    }

    @Test
    void shouldFindLoansByAccountId() {

        Account account = createAccount();

        Loan loan = Loan.builder()
                .account(account)
                .principalAmount(BigDecimal.valueOf(10000))
                .interestRate(BigDecimal.valueOf(10))
                .tenureMonths(12)
                .emiAmount(BigDecimal.valueOf(879))
                .status("APPLIED")
                .createdAt(LocalDateTime.now())
                .build();

        loanRepository.save(loan);

        List<Loan> loans = loanRepository.findByAccountId(account.getId());

        assertThat(loans).hasSize(1);
    }

    @Test
    void shouldFindAllLoans() {

        Account account = createAccount();

        Loan loan = Loan.builder()
                .account(account)
                .principalAmount(BigDecimal.valueOf(15000))
                .interestRate(BigDecimal.valueOf(10))
                .tenureMonths(18)
                .emiAmount(BigDecimal.valueOf(900))
                .status("APPLIED")
                .createdAt(LocalDateTime.now())
                .build();

        loanRepository.save(loan);

        List<Loan> loans = loanRepository.findAll();

        assertThat(loans).isNotEmpty();
    }
}