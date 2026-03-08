package com.fluxbank.corebankingservice.account.service;

import com.fluxbank.corebankingservice.account.dto.AccountRequest;
import com.fluxbank.corebankingservice.account.entity.Account;
import com.fluxbank.corebankingservice.account.repository.AccountRepository;
import com.fluxbank.corebankingservice.user.entity.User;
import com.fluxbank.corebankingservice.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AccountService accountService;

    private AccountRequest buildRequest() {
        AccountRequest req = new AccountRequest();
        req.setUserId(1L);
        req.setAccountType("SAVINGS");
        req.setInitialDeposit(new BigDecimal("1000"));
        return req;
    }

    private Account buildAccount(User user) {
        return Account.builder()
                .id(1L)
                .accountNumber("ACC123")
                .accountType("SAVINGS")
                .balance(new BigDecimal("1000"))
                .status("ACTIVE")
                .user(user)
                .build();
    }

    @Test
    void shouldCreateAccount() {

        AccountRequest request = buildRequest();
        User user = User.builder().id(1L).build();
        Account account = buildAccount(user);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        var response = accountService.createAccount(request);

        assertEquals("ACTIVE", response.getStatus());
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void shouldGetAccount() {

        User user = User.builder().id(1L).build();
        Account account = buildAccount(user);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        var response = accountService.getAccount(1L);

        assertEquals("SAVINGS", response.getAccountType());
    }

    @Test
    void shouldGetAccountsByUser() {

        User user = User.builder().id(1L).build();
        Account account = buildAccount(user);

        when(accountRepository.findByUserId(1L)).thenReturn(List.of(account));

        var accounts = accountService.getAccountsByUser(1L);

        assertEquals(1, accounts.size());
    }

    @Test
    void shouldGetAllAccounts() {

        User user = User.builder().id(1L).build();
        Account account = buildAccount(user);

        when(accountRepository.findAll()).thenReturn(List.of(account));

        var accounts = accountService.getAllAccounts();

        assertEquals(1, accounts.size());
    }

    @Test
    void shouldFreezeAccount() {

        User user = User.builder().id(1L).build();
        Account account = buildAccount(user);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any())).thenReturn(account);

        var response = accountService.freezeAccount(1L);

        assertEquals("FROZEN", response.getStatus());
    }

    @Test
    void shouldCloseAccount() {

        User user = User.builder().id(1L).build();
        Account account = buildAccount(user);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any())).thenReturn(account);

        var response = accountService.closeAccount(1L);

        assertEquals("CLOSED", response.getStatus());
    }
}