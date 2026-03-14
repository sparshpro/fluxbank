package com.fluxbank.corebankingservice.transaction.repository;

import com.fluxbank.corebankingservice.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("""
            SELECT COUNT(t), COALESCE(SUM(t.amount),0)
            FROM Transaction t
            WHERE t.transactionTime >= :start
            AND t.transactionTime < :end
            """)
    Object[] getSummary(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    List<Transaction> findByAccountIdOrderByTransactionTimeDesc(Long accountId);

    @Query("""
            SELECT 
            COALESCE(SUM(CASE WHEN t.transactionType = 'DEPOSIT' THEN 1 ELSE 0 END),0),
            COALESCE(SUM(CASE WHEN t.transactionType = 'WITHDRAW' THEN 1 ELSE 0 END),0),
            COALESCE(SUM(CASE WHEN t.transactionType = 'TRANSFER' THEN 1 ELSE 0 END),0)
            FROM Transaction t
            """)
    List<Object[]> getTransactionStats();
}