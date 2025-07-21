package com.app_bancaria.my_bnl_application.controller;

import com.app_bancaria.my_bnl_application.dto.TransactionDto;
import com.app_bancaria.my_bnl_application.dto.TransactionsRequestDto;
import com.app_bancaria.my_bnl_application.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{accountId}")
    public ResponseEntity<List<TransactionDto>> getBankAccountTransaction(@PathVariable String accountId){
        return ResponseEntity.ok(transactionService.getTransactions(accountId));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/byType")
    public ResponseEntity<List<TransactionDto>> getTransactionsByType(@RequestBody @Valid TransactionsRequestDto request){
        return ResponseEntity.ok(transactionService.getTransactionsByType(request));
    }



}
