package com.fluxbank.advancedfeaturesservice.reporting.repository;

import com.fluxbank.advancedfeaturesservice.fraud.entity.FraudAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ReportRepository extends JpaRepository<FraudAlert, String> {

    @Query("""
        SELECT SUM(fd.principalAmount)
        FROM FixedDeposit fd
    """)
    BigDecimal totalFDInvestments();

    @Query("""
        SELECT SUM(rd.monthlyInstallment * rd.tenureMonths)
        FROM RecurringDeposit rd
    """)
    BigDecimal totalRDInvestments();

    @Query("""
        SELECT f.ruleTriggered, COUNT(f)
        FROM FraudAlert f
        GROUP BY f.ruleTriggered
    """)
    List<Object[]> fraudSummary();
}