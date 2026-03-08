package com.fluxbank.corebankingservice.ledger.service;

import com.fluxbank.corebankingservice.account.entity.Account;
import com.fluxbank.corebankingservice.ledger.entity.LedgerEntry;
import com.fluxbank.corebankingservice.ledger.repository.LedgerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LedgerServiceTest {

    @Mock
    private LedgerRepository ledgerRepository;

    @InjectMocks
    private LedgerService ledgerService;

    @Test
    void shouldCreateLedgerEntry() {

        Account account = Account.builder()
                .id(1L)
                .accountNumber("ACC1001")
                .build();

        ledgerService.createEntry(
                account,
                BigDecimal.valueOf(500),
                "CREDIT",
                "TXN-123"
        );

        verify(ledgerRepository, times(1))
                .save(any(LedgerEntry.class));
    }

    @Test
    void shouldGetEntriesByAccount() {

        LedgerEntry entry = LedgerEntry.builder()
                .entryType("CREDIT")
                .amount(BigDecimal.valueOf(200))
                .transactionReference("TXN-1")
                .build();

        when(ledgerRepository.findAllByAccount_Id(1L))
                .thenReturn(List.of(entry));

        List<LedgerEntry> result = ledgerService.getEntriesByAccount(1L);

        assertThat(result).hasSize(1);
        verify(ledgerRepository).findAllByAccount_Id(1L);
    }

    @Test
    void shouldGetAllEntries() {

        LedgerEntry entry = LedgerEntry.builder()
                .entryType("DEBIT")
                .amount(BigDecimal.valueOf(300))
                .transactionReference("TXN-2")
                .build();

        when(ledgerRepository.findAll())
                .thenReturn(List.of(entry));

        List<LedgerEntry> result = ledgerService.getAllEntries();

        assertThat(result).isNotEmpty();
        verify(ledgerRepository).findAll();
    }
}