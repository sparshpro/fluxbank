package com.fluxbank.advancedfeaturesservice.audit.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AuditLogResponse {

    private UUID id;

    private String userId;

    private String action;

    private String entityType;

    private String entityId;

    private String details;

    private LocalDateTime timestamp;
}
