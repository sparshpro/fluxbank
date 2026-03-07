
package com.fluxbank.advancedfeaturesservice.reporting.controller;


import com.fluxbank.advancedfeaturesservice.reporting.dto.DailyTransactionReport;
import com.fluxbank.advancedfeaturesservice.reporting.dto.DepositSummaryReport;
import com.fluxbank.advancedfeaturesservice.reporting.dto.FraudReport;
import com.fluxbank.advancedfeaturesservice.reporting.service.ReportService;
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
    public DepositSummaryReport getDepositSummary() {
        return reportService.getDepositSummary();
    }

    @GetMapping("/fraud")
    public List<FraudReport> getFraudReport() {
        return reportService.getFraudSummary();
    }



    @GetMapping("/transactions/daily")
    public DailyTransactionReport getDailyTransactions(
            @RequestParam String date
    ) {

        LocalDate reportDate = LocalDate.parse(date);

        return reportService.getDailyTransactionReport(reportDate);
    }
}