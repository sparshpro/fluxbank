package com.fluxbank.advancedfeaturesservice.fixeddeposit.repository;

import com.fluxbank.advancedfeaturesservice.fixeddeposit.entity.FixedDeposit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FixedDepositRepository extends JpaRepository<FixedDeposit, UUID> {
}