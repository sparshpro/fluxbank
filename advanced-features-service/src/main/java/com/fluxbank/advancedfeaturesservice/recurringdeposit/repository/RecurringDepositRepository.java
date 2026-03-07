package com.fluxbank.advancedfeaturesservice.recurringdeposit.repository;

import com.fluxbank.advancedfeaturesservice.recurringdeposit.entity.RecurringDeposit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecurringDepositRepository extends JpaRepository<RecurringDeposit, UUID> {
}