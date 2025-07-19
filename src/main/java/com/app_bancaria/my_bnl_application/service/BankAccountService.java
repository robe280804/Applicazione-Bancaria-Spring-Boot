package com.app_bancaria.my_bnl_application.service;

import com.app_bancaria.my_bnl_application.dto.BankAccountRequestDto;
import com.app_bancaria.my_bnl_application.dto.BankAccountResponseDto;
import com.app_bancaria.my_bnl_application.model.BankAccount;
import com.app_bancaria.my_bnl_application.model.User;
import com.app_bancaria.my_bnl_application.repository.BankAccountRepository;
import com.app_bancaria.my_bnl_application.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;

    @Transactional
    public BankAccountResponseDto create(@Valid BankAccountRequestDto request) {
        log.info("[CREATE] Creazione conto bancario per {}", request.getUserId());

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("User non trovato nel sistema"));

        BankAccount bankAccount = BankAccount.builder()
                .user(user)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .codiceFiscale(request.getCodiceFiscale())
                .tipologia(request.getTipo())
                .valuta(request.getValuta())
                .saldo(request.getSaldo())
                .build();

        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);
        return BankAccountResponseDto.builder()
                .id(savedBankAccount.getId())
                .userId(savedBankAccount.getUser().getId())
                .firstName(savedBankAccount.getFirstName())
                .lastName(savedBankAccount.getLastName())
                .codiceFiscale(savedBankAccount.getCodiceFiscale())
                .iban(savedBankAccount.getIban())
                .numeroConto(savedBankAccount.getNumeroConto())
                .saldo(savedBankAccount.getSaldo())
                .tipologia(savedBankAccount.getTipologia())
                .valuta(savedBankAccount.getValuta())
                .createdAt(savedBankAccount.getCreatedAt())
                .updateAt(savedBankAccount.getUpdateAt())
                .build();
    }

    @Transactional
    public List<BankAccountResponseDto> getUserBankAccount(String userId) {
        log.info("[GET USER ACCOUNTS] Visualizzazione conti bancari per {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User non trovato nel sistema"));

        List<BankAccount> bankAccounts = bankAccountRepository.findAllByUser(user);
        return bankAccounts.stream()
                .map(bankAccount -> BankAccountResponseDto.builder()
                        .id(bankAccount.getId())
                        .userId(bankAccount.getUser().getId())
                        .firstName(bankAccount.getFirstName())
                        .lastName(bankAccount.getLastName())
                        .codiceFiscale(bankAccount.getCodiceFiscale())
                        .iban(bankAccount.getIban())
                        .numeroConto(bankAccount.getNumeroConto())
                        .saldo(bankAccount.getSaldo())
                        .tipologia(bankAccount.getTipologia())
                        .valuta(bankAccount.getValuta())
                        .createdAt(bankAccount.getCreatedAt())
                        .updateAt(bankAccount.getUpdateAt())
                        .build())
                .toList();
    }
}
