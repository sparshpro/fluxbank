package com.fluxbank.corebankingservice.loan.controller;

import com.fluxbank.corebankingservice.loan.dto.LoanResponse;
import com.fluxbank.corebankingservice.loan.service.LoanService;

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
import static org.mockito.ArgumentMatchers.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoanController.class)
class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;

    private LoanResponse buildResponse() {
        return LoanResponse.builder()
                .loanId(1L)
                .principalAmount(BigDecimal.valueOf(100000))
                .interestRate(BigDecimal.valueOf(10.5))
                .tenureMonths(24)
                .emiAmount(BigDecimal.valueOf(4600))
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Should apply for loan")
    void shouldApplyLoan() throws Exception {

        LoanResponse response = buildResponse();

        when(loanService.applyLoan(any())).thenReturn(response);

        mockMvc.perform(post("/loans")
                        .with(jwt())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "principalAmount":100000,
                          "interestRate":10.5,
                          "tenureMonths":24
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loanId").value(1))
                .andExpect(jsonPath("$.principalAmount").value(100000))
                .andExpect(jsonPath("$.status").value("PENDING"));

        verify(loanService).applyLoan(any());
    }

    @Test
    @DisplayName("Should get loan by id")
    void shouldGetLoanById() throws Exception {

        when(loanService.getLoan(1L)).thenReturn(buildResponse());

        mockMvc.perform(get("/loans/1")
                        .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loanId").value(1))
                .andExpect(jsonPath("$.tenureMonths").value(24));

        verify(loanService).getLoan(1L);
    }

    @Test
    @DisplayName("Should get all loans")
    void shouldGetAllLoans() throws Exception {

        when(loanService.getAllLoans())
                .thenReturn(List.of(buildResponse()));

        mockMvc.perform(get("/loans")
                        .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));

        verify(loanService).getAllLoans();
    }

    @Test
    @DisplayName("Should approve loan")
    void shouldApproveLoan() throws Exception {

        LoanResponse response = buildResponse();
        response.setStatus("APPROVED");

        when(loanService.approveLoan(1L)).thenReturn(response);

        mockMvc.perform(put("/loans/1/approve")
                        .with(jwt())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("APPROVED"));

        verify(loanService).approveLoan(1L);
    }

    @Test
    @DisplayName("Should reject loan")
    void shouldRejectLoan() throws Exception {

        LoanResponse response = buildResponse();
        response.setStatus("REJECTED");

        when(loanService.rejectLoan(1L)).thenReturn(response);

        mockMvc.perform(put("/loans/1/reject")
                        .with(jwt())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("REJECTED"));

        verify(loanService).rejectLoan(1L);
    }

    @Test
    @DisplayName("Should close loan")
    void shouldCloseLoan() throws Exception {

        LoanResponse response = buildResponse();
        response.setStatus("CLOSED");

        when(loanService.closeLoan(1L)).thenReturn(response);

        mockMvc.perform(put("/loans/1/close")
                        .with(jwt())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CLOSED"));

        verify(loanService).closeLoan(1L);
    }
}