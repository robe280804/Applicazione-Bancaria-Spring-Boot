package com.app_bancaria.my_bnl_application.repository;

import com.app_bancaria.my_bnl_application.model.BankAccount;
import com.app_bancaria.my_bnl_application.model.Transaction;
import com.app_bancaria.my_bnl_application.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionsRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByFromAccount(BankAccount bankAccount);
    List<Transaction> findByFromAccountAndType(BankAccount bankAccount, TransactionType type);
}
