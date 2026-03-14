package com.fluxbank.advancedfeaturesservice.audit.controller;

import com.fluxbank.advancedfeaturesservice.audit.dto.AuditLogRequest;
import com.fluxbank.advancedfeaturesservice.audit.dto.AuditLogResponse;
import com.fluxbank.advancedfeaturesservice.audit.entity.AuditLog;
import com.fluxbank.advancedfeaturesservice.audit.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;


    @PostMapping
    @PreAuthorize("hasRole('SYSTEM')")
    public AuditLogResponse logAction(@RequestBody AuditLogRequest request) {
        return auditService.logAction(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','AUDITOR')")
    public List<AuditLog> getAllLogs() {
        return auditService.getAllLogs();
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','AUDITOR','MANAGER')")
    public List<AuditLog> getUserLogs(@PathVariable String userId) {
        return auditService.getLogsByUser(userId);
    }
}