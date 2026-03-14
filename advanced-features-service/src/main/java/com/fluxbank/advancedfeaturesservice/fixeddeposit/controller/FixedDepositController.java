package com.fluxbank.advancedfeaturesservice.fixeddeposit.controller;

import com.fluxbank.advancedfeaturesservice.fixeddeposit.dto.CreateFDRequest;
import com.fluxbank.advancedfeaturesservice.fixeddeposit.dto.FDResponse;
import com.fluxbank.advancedfeaturesservice.fixeddeposit.service.FixedDepositService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fd")
public class FixedDepositController {

    private final FixedDepositService fdService;

    public FixedDepositController(FixedDepositService fdService) {
        this.fdService = fdService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER','BANK_STAFF','MANAGER','ADMIN')")
    public FDResponse createFD(@RequestBody CreateFDRequest request) {
        return fdService.createFD(request);
    }
}