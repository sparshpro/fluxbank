package com.fluxbank.corebankingservice.account.repository;

import com.fluxbank.corebankingservice.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByUserId(Long userId);
}