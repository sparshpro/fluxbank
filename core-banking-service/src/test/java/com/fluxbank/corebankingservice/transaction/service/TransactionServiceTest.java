package com.fluxbank.corebankingservice.transaction.service;

import com.fluxbank.corebankingservice.account.entity.Account;
import com.fluxbank.corebankingservice.account.repository.AccountRepository;
import com.fluxbank.corebankingservice.ledger.repository.LedgerRepository;
import com.fluxbank.corebankingservice.transaction.dto.*;
import com.fluxbank.corebankingservice.transaction.entity.Transaction;
import com.fluxbank.corebankingservice.transaction.repository.TransactionRepository;

import org.junit.jupiter.api.BeforeEach;
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
class TransactionServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private LedgerRepository ledgerRepository;

    @InjectMocks
    private TransactionService transactionService;

    private Account account;

    @BeforeEach
    void setup() {
        account = new Account();
        account.setId(1L);
        account.setBalance(BigDecimal.valueOf(10000));
    }

    @Test
    void deposit_shouldIncreaseBalanceAndCreateTransaction() {

        DepositRequest request = new DepositRequest(1L, BigDecimal.valueOf(1000));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Transaction tx = Transaction.builder()
                .transactionReference("TXN-1")
                .transactionType("DEPOSIT")
                .amount(BigDecimal.valueOf(1000))
                .account(account)
                .transactionTime(LocalDateTime.now())
                .status("SUCCESS")
                .direction("CREDIT")
                .build();

        when(transactionRepository.save(any())).thenReturn(tx);

        TransactionResponse response = transactionService.deposit(request);

        assertNotNull(response);

        verify(accountRepository).save(account);
        verify(transactionRepository).save(any(Transaction.class));
        verify(ledgerRepository).save(any());
    }

    @Test
    void withdraw_shouldDecreaseBalance() {

        WithdrawRequest request = new WithdrawRequest(1L, BigDecimal.valueOf(500));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Transaction tx = Transaction.builder()
                .transactionReference("TXN-2")
                .transactionType("WITHDRAW")
                .amount(BigDecimal.valueOf(500))
                .account(account)
                .transactionTime(LocalDateTime.now())
                .status("SUCCESS")
                .direction("DEBIT")
                .build();

        when(transactionRepository.save(any())).thenReturn(tx);

        TransactionResponse response = transactionService.withdraw(request);

        assertNotNull(response);

        verify(accountRepository).save(account);
        verify(transactionRepository).save(any());
        verify(ledgerRepository).save(any());
    }

    @Test
    void withdraw_shouldThrowException_whenInsufficientBalance() {

        WithdrawRequest request = new WithdrawRequest(1L, BigDecimal.valueOf(20000));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> transactionService.withdraw(request)
        );

        assertEquals("Insufficient balance", ex.getMessage());
    }

    @Test
    void transfer_shouldMoveMoneyBetweenAccounts() {

        Account toAccount = new Account();
        toAccount.setId(2L);
        toAccount.setBalance(BigDecimal.valueOf(5000));

        TransferRequest request =
                new TransferRequest(1L, 2L, BigDecimal.valueOf(1000));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));

        Transaction tx = Transaction.builder()
                .transactionReference("TXN-3")
                .transactionType("TRANSFER")
                .amount(BigDecimal.valueOf(1000))
                .account(account)
                .transactionTime(LocalDateTime.now())
                .status("SUCCESS")
                .direction("DEBIT")
                .build();

        when(transactionRepository.save(any())).thenReturn(tx);

        TransactionResponse response = transactionService.transfer(request);

        assertNotNull(response);

        verify(accountRepository, times(2)).save(any(Account.class));
        verify(transactionRepository, times(2)).save(any(Transaction.class));
        verify(ledgerRepository, times(2)).save(any());
    }

    @Test
    void transfer_shouldThrowException_whenInsufficientBalance() {

        Account toAccount = new Account();
        toAccount.setId(2L);
        toAccount.setBalance(BigDecimal.valueOf(5000));

        account.setBalance(BigDecimal.valueOf(100));

        TransferRequest request =
                new TransferRequest(1L, 2L, BigDecimal.valueOf(1000));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> transactionService.transfer(request)
        );

        assertEquals("Insufficient balance", ex.getMessage());
    }

    @Test
    void getAccountTransactions_shouldReturnHistory() {

        Transaction tx = Transaction.builder()
                .transactionReference("TXN-10")
                .transactionType("DEPOSIT")
                .amount(BigDecimal.valueOf(1000))
                .transactionTime(LocalDateTime.now())
                .direction("CREDIT")
                .build();

        when(transactionRepository.findByAccountIdOrderByTransactionTimeDesc(1L))
                .thenReturn(List.of(tx));

        List<TransactionHistoryResponse> history =
                transactionService.getAccountTransactions(1L);

        assertEquals(1, history.size());
        assertEquals("TXN-10", history.get(0).getReference());

        verify(transactionRepository)
                .findByAccountIdOrderByTransactionTimeDesc(1L);
    }
}