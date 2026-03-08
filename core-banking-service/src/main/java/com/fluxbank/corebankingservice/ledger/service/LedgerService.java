package com.fluxbank.corebankingservice.ledger.service;

import com.fluxbank.corebankingservice.account.entity.Account;
import com.fluxbank.corebankingservice.ledger.entity.LedgerEntry;
import com.fluxbank.corebankingservice.ledger.repository.LedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LedgerService {

    private final LedgerRepository ledgerRepository;

    public void createEntry(Account account,
                            BigDecimal amount,
                            String type,
                            String reference){

        LedgerEntry entry = LedgerEntry.builder()
                .account(account)
                .amount(amount)
                .entryType(type)
                .transactionReference(reference)
                .createdAt(LocalDateTime.now())
                .build();

        ledgerRepository.save(entry);
    }


    public List<LedgerEntry> getEntriesByAccount(Long accountId){

        return ledgerRepository.findAllByAccount_Id(accountId);
    }


    public List<LedgerEntry> getAllEntries(){
        return ledgerRepository.findAll();
    }
}