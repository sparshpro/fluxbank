package com.fluxbank.corebankingservice.loan.repository;

import com.fluxbank.corebankingservice.loan.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByAccountId(Long accountId);

}