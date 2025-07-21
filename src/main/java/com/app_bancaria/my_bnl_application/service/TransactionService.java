package com.app_bancaria.my_bnl_application.service;

import com.app_bancaria.my_bnl_application.dto.TransactionDto;
import com.app_bancaria.my_bnl_application.dto.TransactionsRequestDto;
import com.app_bancaria.my_bnl_application.model.BankAccount;
import com.app_bancaria.my_bnl_application.model.Transaction;
import com.app_bancaria.my_bnl_application.repository.BankAccountRepository;
import com.app_bancaria.my_bnl_application.repository.TransactionsRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionService {

    private final SecurityService securityService;
    private final TransactionsRepository transactionsRepository;
    private final BankAccountRepository bankAccountRepository;

    @Transactional(readOnly = true)
    public List<TransactionDto> getTransactions(String accountId) {
        String userId = securityService.getCurrentUserId();

        BankAccount bankAccount = bankAccountRepository.findByIdAndUserId(accountId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Conto non trovato per questo utente"));

        List<Transaction> transactions = transactionsRepository.findByFromAccount(bankAccount);
        return transactions.stream()
                .map(transaction -> TransactionDto.builder()
                        .id(transaction.getId())
                        .motivation(transaction.getMotivation())
                        .amount(transaction.getAmount())
                        .bankAccountId(bankAccount.getId())
                                .type(transaction.getType())
                        .date(transaction.getDate())
                        .build()
                        )
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TransactionDto> getTransactionsByType(@Valid TransactionsRequestDto request) {
        String userId = securityService.getCurrentUserId();

        BankAccount bankAccount = bankAccountRepository.findByIdAndUserId(request.getIdAccount(), userId)
                .orElseThrow(() -> new EntityNotFoundException("Conto non trovato per questo utente"));

        return transactionsRepository.findByFromAccountAndType(bankAccount, request.getTransactionType()).stream()
                .map(transaction -> TransactionDto.builder()
                        .id(transaction.getId())
                        .motivation(transaction.getMotivation())
                        .amount(transaction.getAmount())
                        .bankAccountId(bankAccount.getId())
                        .type(transaction.getType())
                        .date(transaction.getDate())
                        .build()
                )
                .collect(Collectors.toList());
    }
}
