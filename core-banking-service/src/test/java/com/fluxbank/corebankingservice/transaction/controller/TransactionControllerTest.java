package com.fluxbank.corebankingservice.transaction.controller;

import com.fluxbank.corebankingservice.transaction.dto.TransactionHistoryResponse;
import com.fluxbank.corebankingservice.transaction.dto.TransactionResponse;
import com.fluxbank.corebankingservice.transaction.service.TransactionService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    private TransactionResponse buildResponse() {
        return TransactionResponse.builder()
                .reference("TXN12345")
                .type("DEPOSIT")
                .amount(BigDecimal.valueOf(5000))
                .time(LocalDateTime.now())
                .build();
    }

    private TransactionHistoryResponse buildHistory() {
        return TransactionHistoryResponse.builder()
                .reference("TXN12345")
                .type("DEPOSIT")
                .amount(BigDecimal.valueOf(5000))
                .direction("CREDIT")
                .time(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Should deposit money")
    void shouldDeposit() throws Exception {

        TransactionResponse response = buildResponse();

        when(transactionService.deposit(any())).thenReturn(response);

        mockMvc.perform(post("/transactions/deposit")
                        .with(jwt().authorities(() -> "ROLE_BANK_STAFF"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "accountId":1,
                          "amount":5000
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reference").value("TXN12345"))
                .andExpect(jsonPath("$.type").value("DEPOSIT"))
                .andExpect(jsonPath("$.amount").value(5000));

        verify(transactionService).deposit(any());
    }

    @Test
    @DisplayName("Should withdraw money")
    void shouldWithdraw() throws Exception {

        TransactionResponse response = buildResponse();

        when(transactionService.withdraw(any())).thenReturn(response);

        mockMvc.perform(post("/transactions/withdraw")
                        .with(jwt().authorities(() -> "ROLE_BANK_STAFF"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "accountId":1,
                          "amount":2000
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reference").value("TXN12345"))
                .andExpect(jsonPath("$.amount").value(5000));

        verify(transactionService).withdraw(any());
    }

    @Test
    @DisplayName("Should transfer money")
    void shouldTransfer() throws Exception {

        TransactionResponse response = buildResponse();

        when(transactionService.transfer(any())).thenReturn(response);

        mockMvc.perform(post("/transactions/transfer")
                        .with(jwt().authorities(() -> "ROLE_CUSTOMER"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "fromAccountId":1,
                          "toAccountId":2,
                          "amount":1500
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reference").value("TXN12345"))
                .andExpect(jsonPath("$.type").value("DEPOSIT"));

        verify(transactionService).transfer(any());
    }

    @Test
    @DisplayName("Should get account transactions")
    void shouldGetAccountTransactions() throws Exception {

        when(transactionService.getAccountTransactions(1L))
                .thenReturn(List.of(buildHistory()));

        mockMvc.perform(get("/transactions/account/1")
                        .with(jwt().authorities(() -> "ROLE_CUSTOMER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].reference").value("TXN12345"))
                .andExpect(jsonPath("$[0].direction").value("CREDIT"));

        verify(transactionService).getAccountTransactions(1L);
    }
}