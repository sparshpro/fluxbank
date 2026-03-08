package com.fluxbank.corebankingservice.account.controller;

import com.fluxbank.corebankingservice.account.dto.AccountResponse;
import com.fluxbank.corebankingservice.account.service.AccountService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    private AccountResponse buildResponse() {
        return AccountResponse.builder()
                .id(1L)
                .accountNumber("ACC123")
                .accountType("SAVINGS")
                .balance(BigDecimal.valueOf(1000))
                .status("ACTIVE")
                .userId(1L)
                .build();
    }

    @Test
    @DisplayName("Should create account")
    void shouldCreateAccount() throws Exception {

        AccountResponse response = buildResponse();

        when(accountService.createAccount(any())).thenReturn(response);

        mockMvc.perform(post("/accounts")
                        .with(jwt())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "accountType":"SAVINGS",
                                  "userId":1
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("ACC123"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

        verify(accountService).createAccount(any());
    }

    @Test
    @DisplayName("Should get account by id")
    void shouldGetAccount() throws Exception {

        AccountResponse response = buildResponse();

        when(accountService.getAccount(1L)).thenReturn(response);

        mockMvc.perform(get("/accounts/1")
                        .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.accountNumber").value("ACC123"));

        verify(accountService).getAccount(1L);
    }

    @Test
    @DisplayName("Should get accounts by user")
    void shouldGetAccountsByUser() throws Exception {

        when(accountService.getAccountsByUser(1L))
                .thenReturn(List.of(buildResponse()));

        mockMvc.perform(get("/accounts/user/1")
                        .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));

        verify(accountService).getAccountsByUser(1L);
    }

    @Test
    @DisplayName("Should freeze account")
    void shouldFreezeAccount() throws Exception {

        AccountResponse response = buildResponse();
        response.setStatus("FROZEN");

        when(accountService.freezeAccount(1L)).thenReturn(response);

        mockMvc.perform(put("/accounts/1/freeze")
                        .with(jwt())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("FROZEN"));

        verify(accountService).freezeAccount(1L);
    }

    @Test
    @DisplayName("Should close account")
    void shouldCloseAccount() throws Exception {

        AccountResponse response = buildResponse();
        response.setStatus("CLOSED");

        when(accountService.closeAccount(1L)).thenReturn(response);

        mockMvc.perform(put("/accounts/1/close")
                        .with(jwt())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CLOSED"));

        verify(accountService).closeAccount(1L);
    }

    @Test
    @DisplayName("Should return all accounts")
    void shouldReturnAllAccounts() throws Exception {

        when(accountService.getAllAccounts())
                .thenReturn(List.of(buildResponse()));

        mockMvc.perform(get("/accounts")
                        .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));

        verify(accountService).getAllAccounts();
    }

    @Test
    @DisplayName("Should return error when account not found")
    void shouldReturnErrorWhenAccountNotFound() throws Exception {

        when(accountService.getAccount(1L))
                .thenThrow(new RuntimeException("Account not found"));

        mockMvc.perform(get("/accounts/1")
                        .with(jwt()))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Account not found"));
    }
}