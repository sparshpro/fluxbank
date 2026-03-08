package com.fluxbank.corebankingservice.loan.service;

import com.fluxbank.corebankingservice.account.entity.Account;
import com.fluxbank.corebankingservice.account.repository.AccountRepository;
import com.fluxbank.corebankingservice.loan.dto.LoanRequest;
import com.fluxbank.corebankingservice.loan.entity.Loan;
import com.fluxbank.corebankingservice.loan.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private LoanService loanService;

    private LoanRequest buildRequest() {
        LoanRequest request = new LoanRequest();
        request.setAccountId(1L);
        request.setAmount(new BigDecimal("10000"));
        request.setTenureMonths(12);
        return request;
    }

    private Loan buildLoan(Account account) {
        return Loan.builder()
                .id(1L)
                .account(account)
                .principalAmount(new BigDecimal("10000"))
                .interestRate(new BigDecimal("10"))
                .tenureMonths(12)
                .emiAmount(new BigDecimal("879.16"))
                .status("APPLIED")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldApplyLoan() {

        LoanRequest request = buildRequest();
        Account account = Account.builder().id(1L).build();
        Loan loan = buildLoan(account);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(loanRepository.save(any())).thenReturn(loan);

        var response = loanService.applyLoan(request);

        assertEquals("APPLIED", response.getStatus());
        verify(loanRepository).save(any(Loan.class));
    }

    @Test
    void shouldGetLoan() {

        Account account = Account.builder().id(1L).build();
        Loan loan = buildLoan(account);

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));

        var response = loanService.getLoan(1L);

        assertEquals(new BigDecimal("10000"), response.getPrincipalAmount());
    }

    @Test
    void shouldUpdateLoan() {

        LoanRequest request = buildRequest();
        Account account = Account.builder().id(1L).build();
        Loan loan = buildLoan(account);

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any())).thenReturn(loan);

        var response = loanService.updateLoan(1L, request);

        assertEquals(new BigDecimal("10000"), response.getPrincipalAmount());
        verify(loanRepository).save(any());
    }

    @Test
    void shouldApproveLoan() {

        Account account = Account.builder().id(1L).build();
        Loan loan = buildLoan(account);

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any())).thenReturn(loan);

        var response = loanService.approveLoan(1L);

        assertEquals("APPROVED", response.getStatus());
    }

    @Test
    void shouldRejectLoan() {

        Account account = Account.builder().id(1L).build();
        Loan loan = buildLoan(account);

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any())).thenReturn(loan);

        var response = loanService.rejectLoan(1L);

        assertEquals("REJECTED", response.getStatus());
    }

    @Test
    void shouldCloseLoan() {

        Account account = Account.builder().id(1L).build();
        Loan loan = buildLoan(account);

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any())).thenReturn(loan);

        var response = loanService.closeLoan(1L);

        assertEquals("CLOSED", response.getStatus());
    }

    @Test
    void shouldDeleteLoan() {

        Account account = Account.builder().id(1L).build();
        Loan loan = buildLoan(account);

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));

        loanService.deleteLoan(1L);

        verify(loanRepository).delete(loan);
    }

    @Test
    void shouldReturnLoansByAccountId() {

        Account account = Account.builder().id(1L).build();
        Loan loan = buildLoan(account);

        when(loanRepository.findByAccountId(1L)).thenReturn(List.of(loan));

        var loans = loanService.getLoansByUserId(1L);

        assertEquals(1, loans.size());
    }

    @Test
    void shouldReturnAllLoans() {

        Account account = Account.builder().id(1L).build();
        Loan loan = buildLoan(account);

        when(loanRepository.findAll()).thenReturn(List.of(loan));

        var loans = loanService.getAllLoans();

        assertEquals(1, loans.size());
    }
}