
package com.fluxbank.advancedfeaturesservice.reporting.controller;


import com.fluxbank.advancedfeaturesservice.reporting.dto.*;
import com.fluxbank.advancedfeaturesservice.reporting.service.ReportService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/deposits")
    @PreAuthorize("hasAnyRole('ADMIN', 'AUDITOR')")
    public DepositSummaryReport getDepositSummary() {
        return reportService.getDepositSummary();
    }

    @GetMapping("/fraud")
    @PreAuthorize("hasAnyRole('ADMIN', 'AUDITOR')")
    public List<FraudReport> getFraudReport() {
        return reportService.getFraudSummary();
    }


    @GetMapping("/daily")
    @PreAuthorize("hasAnyRole('ADMIN', 'AUDITOR', 'MANAGER')")
    public DailyTransactionReport dailyReport(
            @RequestParam LocalDate date) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("Authorities: " + auth.getAuthorities());
        return reportService.getDailyReport(date);
    }

    @GetMapping("/monthly")
    @PreAuthorize("hasAnyRole('ADMIN', 'AUDITOR', 'MANAGER')")
    public MonthlyTransactionReport monthlyReport(
            @RequestParam int year,
            @RequestParam int month) {
        return reportService.getMonthlyReport(year, month);
    }

    @GetMapping("/stats")
    @PreAuthorize("hasAnyRole('ADMIN', 'AUDITOR')")
    public TransactionStats stats() {
        return reportService.getTransactionStats();
    }
}