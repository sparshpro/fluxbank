package com.fluxbank.corebankingservice.account.service;

import com.fluxbank.corebankingservice.account.dto.AccountRequest;
import com.fluxbank.corebankingservice.account.dto.AccountResponse;
import com.fluxbank.corebankingservice.account.entity.Account;
import com.fluxbank.corebankingservice.account.mapper.AccountMapper;
import com.fluxbank.corebankingservice.account.repository.AccountRepository;
import com.fluxbank.corebankingservice.user.entity.User;
import com.fluxbank.corebankingservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    /// /////// CREATE ACCOUNT //////////
    public AccountResponse createAccount(AccountRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = Account.builder()
                .accountNumber(generateAccountNumber())
                .accountType(request.getAccountType())
                .balance(request.getInitialDeposit() == null
                        ? BigDecimal.ZERO
                        : request.getInitialDeposit())
                .status("ACTIVE")
                .user(user)
                .build();

        return AccountMapper.toResponse(accountRepository.save(account));
    }

    /// /////// GET ACCOUNT //////////
    public AccountResponse getAccount(Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return AccountMapper.toResponse(account);
    }

    /// /////// GET ACCOUNTS BY USER //////////
    public List<AccountResponse> getAccountsByUser(Long userId) {

        return accountRepository.findByUserId(userId)
                .stream()
                .map(AccountMapper::toResponse)
                .toList();
    }

    /// ////// GET ALL ACCOUNTS //////////
    public List<AccountResponse> getAllAccounts() {

        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(AccountMapper::toResponse)
                .toList();
    }

    /// /////// FREEZE ACCOUNT //////////
    public AccountResponse freezeAccount(Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setStatus("FROZEN");

        return AccountMapper.toResponse(accountRepository.save(account));
    }

    /// /////// CLOSE ACCOUNT //////////
    public AccountResponse closeAccount(Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setStatus("CLOSED");

        return AccountMapper.toResponse(accountRepository.save(account));
    }

    /// /////// GENERATE ACCOUNT NUMBER //////////
    private String generateAccountNumber() {

        return "ACC" + System.currentTimeMillis();
    }


}