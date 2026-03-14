package com.fluxbank.advancedfeaturesservice.fraud.repository;

import com.fluxbank.advancedfeaturesservice.fraud.entity.FraudAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.UUID;

public interface FraudRepository extends JpaRepository<FraudAlert, UUID> {

    @Query("""
        SELECT COUNT(f)
        FROM FraudAlert f
        WHERE f.accountId = :accountId
        AND f.timestamp >= :time
    """)
    long countRecentTransactions(String accountId, LocalDateTime time);
}