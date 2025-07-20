package com.app_bancaria.my_bnl_application.repository;

import com.app_bancaria.my_bnl_application.model.BankAccount;
import com.app_bancaria.my_bnl_application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    List<BankAccount> findAllByUser(User user);

    Optional<BankAccount> findByIdAndUserId(String idAccount, String idUser);
}
