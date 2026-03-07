package com.fluxbank.corebankingservice.transaction.service;

import com.fluxbank.corebankingservice.account.entity.Account;
import com.fluxbank.corebankingservice.account.repository.AccountRepository;
import com.fluxbank.corebankingservice.ledger.entity.LedgerEntry;
import com.fluxbank.corebankingservice.ledger.repository.LedgerRepository;
import com.fluxbank.corebankingservice.transaction.dto.*;
import com.fluxbank.corebankingservice.transaction.entity.Transaction;
import com.fluxbank.corebankingservice.transaction.mapper.TransactionMapper;
import com.fluxbank.corebankingservice.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final LedgerRepository ledgerRepository;

    /// /////// DEPOSIT //////////
    @Transactional
    public TransactionResponse deposit(DepositRequest request) {

        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance().add(request.getAmount()));
        accountRepository.save(account);

        String reference = generateReference();

        Transaction tx = Transaction.builder()
                .transactionReference(reference)
                .transactionType("DEPOSIT")
                .amount(request.getAmount())
                .account(account)
                .transactionTime(LocalDateTime.now())
                .status("SUCCESS")
                .direction("CREDIT")
                .build();

        Transaction savedTxn = transactionRepository.save(tx);

        createLedgerEntry(account, request.getAmount(), "CREDIT", reference, LocalDateTime.now());

        return TransactionMapper.toTransactionResponse(savedTxn);
    }

    /// /////// WITHDRAW //////////
    @Transactional
    public TransactionResponse withdraw(WithdrawRequest request) {

        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if(account.getBalance().compareTo(request.getAmount()) < 0){
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(request.getAmount()));
        accountRepository.save(account);

        String reference = generateReference();

        Transaction tx = Transaction.builder()
                .transactionReference(reference)
                .transactionType("WITHDRAW")
                .amount(request.getAmount())
                .account(account)
                .transactionTime(LocalDateTime.now())
                .status("SUCCESS")
                .direction("DEBIT")
                .build();

        Transaction savedTxn = transactionRepository.save(tx);

        createLedgerEntry(account, request.getAmount(), "DEBIT", reference,LocalDateTime.now());

        return TransactionMapper.toTransactionResponse(savedTxn);
    }

    /// /////// TRANSFER //////////
    @Transactional
    public TransactionResponse transfer(TransferRequest request) {

        Account from = accountRepository.findById(request.getFromAccountId())
                .orElseThrow(() -> new RuntimeException("Source account not found"));

        Account to = accountRepository.findById(request.getToAccountId())
                .orElseThrow(() -> new RuntimeException("Destination account not found"));

        if(from.getBalance().compareTo(request.getAmount()) < 0){
            throw new RuntimeException("Insufficient balance");
        }

        from.setBalance(from.getBalance().subtract(request.getAmount()));
        to.setBalance(to.getBalance().add(request.getAmount()));

        accountRepository.save(from);
        accountRepository.save(to);

        String reference = generateReference();

        Transaction debitTx = Transaction.builder()
                .transactionReference(reference)
                .transactionType("TRANSFER")
                .amount(request.getAmount())
                .transactionTime(LocalDateTime.now())
                .status("SUCCESS")
                .account(from)
                .direction("DEBIT")
                .build();

        Transaction creditTx = Transaction.builder()
                .transactionReference(reference)
                .transactionType("TRANSFER")
                .amount(request.getAmount())
                .transactionTime(LocalDateTime.now())
                .status("SUCCESS")
                .account(to)
                .direction("CREDIT")
                .build();

        Transaction savedTxn = transactionRepository.save(debitTx);
        transactionRepository.save(creditTx);

        createLedgerEntry(from, request.getAmount(), "DEBIT", reference, LocalDateTime.now());
        createLedgerEntry(to, request.getAmount(), "CREDIT", reference, LocalDateTime.now());

        return TransactionMapper.toTransactionResponse(savedTxn);
    }

    /// /////// GET TRANSACTION HISTORY //////////
    public List<TransactionHistoryResponse> getAccountTransactions(Long accountId) {

        return transactionRepository
                .findByAccountIdOrderByTransactionTimeDesc(accountId)
                .stream()
                .map(tx -> TransactionHistoryResponse.builder()
                        .reference(tx.getTransactionReference())
                        .type(tx.getTransactionType())
                        .amount(tx.getAmount())
                        .time(tx.getTransactionTime())
                        .direction(tx.getDirection())
                        .build())
                .toList();
    }

    /// /////// LEDGER ENTRY //////////
    private void createLedgerEntry(Account account, BigDecimal amount, String type, String reference, LocalDateTime time) {

        LedgerEntry entry = LedgerEntry.builder()
                .account(account)
                .amount(amount)
                .entryType(type)
                .transactionReference(reference)
                .createdAt(time)
                .build();

        ledgerRepository.save(entry);
    }

    /// /////// GENERATE REFERENCE //////////
    private String generateReference() {
        return "TXN-" + System.currentTimeMillis();
    }
}