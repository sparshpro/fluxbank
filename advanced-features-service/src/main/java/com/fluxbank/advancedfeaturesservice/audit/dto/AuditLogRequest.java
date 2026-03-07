package com.fluxbank.advancedfeaturesservice.audit.dto;

import lombok.*;

@Data
public class AuditLogRequest {

    private String userId;

    private String action;

    private String entityType;

    private String entityId;

    private String details;
}