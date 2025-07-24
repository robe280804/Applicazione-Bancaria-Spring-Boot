package com.app_bancaria.my_bnl_application.repository;

import com.app_bancaria.my_bnl_application.model.BankAccount;
import com.app_bancaria.my_bnl_application.model.User;
import jakarta.persistence.LockModeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    List<BankAccount> findAllByUser(User user);

    @Lock(LockModeType.PESSIMISTIC_WRITE)  //blocco la riga nel db interessata per evitate conflitti
    Optional<BankAccount> findByIdAndUserId(String idAccount, String idUser);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<BankAccount> findByIban(String iban);
}
