package com.fluxbank.corebankingservice.ledger.repository;

import com.fluxbank.corebankingservice.ledger.entity.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LedgerRepository extends JpaRepository<LedgerEntry, Long> {

    List<LedgerEntry> findByAccountId(Long accountId);
}