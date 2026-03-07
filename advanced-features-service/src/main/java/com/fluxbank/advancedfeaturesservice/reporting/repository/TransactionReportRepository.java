package com.fluxbank.advancedfeaturesservice.reporting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface TransactionReportRepository extends Repository<Object, Long> {

    @Query(value = """
        SELECT COUNT(*) AS totalTransactions,
               COALESCE(SUM(amount),0) AS totalAmount
        FROM transactions
        WHERE DATE(created_at) = :date
        """, nativeQuery = true)
    Object[] dailyTransactionSummary(@Param("date") LocalDate date);
}