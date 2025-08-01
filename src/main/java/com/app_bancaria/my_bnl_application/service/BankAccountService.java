package com.app_bancaria.my_bnl_application.service;

import com.app_bancaria.my_bnl_application.dto.*;
import com.app_bancaria.my_bnl_application.exception.PasswordNotValidEx;
import com.app_bancaria.my_bnl_application.exception.SaldoNonDisponibileEx;
import com.app_bancaria.my_bnl_application.model.*;
import com.app_bancaria.my_bnl_application.repository.BankAccountRepository;
import com.app_bancaria.my_bnl_application.repository.TransactionsRepository;
import com.app_bancaria.my_bnl_application.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final SecurityService securityService;
    private final TransactionsRepository transactionsRepository;
    private final EmailSenderService emailSenderService;

    @Transactional
    public BankAccountResponseDto create(@Valid BankAccountRequestDto request) {

        String userId = securityService.getCurrentUserId();

        log.info("[CREATE] Creazione conto bancario per user con id {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User non trovato nel sistema"));

        BankAccount bankAccount = BankAccount.builder()
                .user(user)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .codiceFiscale(request.getCodiceFiscale())
                .tipologia(request.getType())
                .valuta(request.getValuta())
                .saldo(request.getSaldo())
                .build();

        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);
        bankAccountRepository.flush();
        log.info("[CREATE] Conto bancario con id {} creato con successo", savedBankAccount.getId());

        /*   Invio Email Disabilitato
        emailSenderService.sendEmail(user.getEmail(), String.format(
                "Creazione del conto bancario con IBAN %s creato con successo", bankAccount.getIban()));
        */

        return BankAccountResponseDto.builder()
                .id(savedBankAccount.getId())
                .userId(savedBankAccount.getUser().getId())
                .firstName(savedBankAccount.getFirstName())
                .lastName(savedBankAccount.getLastName())
                .codiceFiscale(savedBankAccount.getCodiceFiscale())
                .iban(savedBankAccount.getIban())
                .numeroConto(savedBankAccount.getNumeroConto())
                .saldo(savedBankAccount.getSaldo())
                .type(savedBankAccount.getTipologia())
                .valuta(savedBankAccount.getValuta())
                .createdAt(savedBankAccount.getCreatedAt())
                .updatedAt(savedBankAccount.getUpdatedAt())
                .message("Creazione del conto bancario andata a buon fine")
                .build();
    }

    @Transactional(readOnly = true)
    public List<BankAccountResponseDto> getUserBankAccount() {

        String userId = securityService.getCurrentUserId();

        log.info("[VISUALIZZA CONTI] Ottieni conti bancari per user con id {}", userId);

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
                        .type(bankAccount.getTipologia())
                        .valuta(bankAccount.getValuta())
                        .createdAt(bankAccount.getCreatedAt())
                        .updatedAt(bankAccount.getUpdatedAt())
                        .build())
                .toList();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public TransazioneResponseDto deposita(TransazioneRequestDto request) {
        log.info("[DEPOSITO] Chiamata al metodo di deposito");

        String userId = securityService.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User non trovato"));

        if (!securityService.verifyPassword(user, request.getPassword())) {
            throw new PasswordNotValidEx("Password errata");
        }

        BankAccount bankAccount = bankAccountRepository.findByIdAndUserId(request.getAccountId(), userId)
                .orElseThrow(() -> new EntityNotFoundException("Conto non trovato per questo utente"));

        bankAccount.setSaldo(bankAccount.getSaldo().add(request.getAmount()));
        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);

        /* Invio Email disabilitato
        emailSenderService.sendEmail(user.getEmail(), String.format(
                "Deposito eseguito sul tuo conto %S di %,.2f %s",
                bankAccount.getIban(), request.getAmount(), bankAccount.getValuta()));
         */

        log.info("[DEPOSITO] Utente {} deposita {} su conto {}", userId, request.getAmount(), request.getAccountId());

        //Gestione transazione
        Transaction transaction = Transaction.builder()
                .motivation(request.getMotivation())
                .amount(request.getAmount())
                .fromAccount(savedBankAccount)
                .type(TransactionType.DEPOSITO)
                .date(LocalDateTime.now())
                .build();

        transactionsRepository.save(transaction);

        return TransazioneResponseDto.builder()
                .contoId(savedBankAccount.getId())
                .iban(savedBankAccount.getIban())
                .amount(request.getAmount())
                .saldoAttuale(savedBankAccount.getSaldo())
                .message("Deposito andato a buon fine")
                .build();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public TransazioneResponseDto preleva(TransazioneRequestDto request) {
        log.info("[PRELEVA] Chiamata al metodo preleva");

        String userId = securityService.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User non trovato"));

        if (!securityService.verifyPassword(user, request.getPassword())) {
            throw new PasswordNotValidEx("Password errata");
        }

        BankAccount bankAccount = bankAccountRepository.findByIdAndUserId(request.getAccountId(), userId)
                .orElseThrow(() -> new EntityNotFoundException("Conto non trovato per questo utente"));

        if (bankAccount.getSaldo().compareTo(request.getAmount()) < 0){
            throw new SaldoNonDisponibileEx("Saldo insufficente, inserisci una somma minore o deposita");
        }

        bankAccount.setSaldo(bankAccount.getSaldo().subtract(request.getAmount()));
        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);

        log.info("[PRELEVA] Utente {} preleva {} su conto {}", userId, request.getAmount(), request.getAccountId());

        /* Invio Email disabilitato
        emailSenderService.sendEmail(user.getEmail(), String.format(
                "Prelievo eseguito sul tuo conto %S di %,.2f %s",
                bankAccount.getIban(), request.getAmount(), bankAccount.getValuta()));

         */

        Transaction transaction = Transaction.builder()
                .motivation(request.getMotivation())
                .amount(request.getAmount())
                .fromAccount(savedBankAccount)
                .type(TransactionType.PRELIEVO)
                .date(LocalDateTime.now())
                .build();

        transactionsRepository.save(transaction);

        return TransazioneResponseDto.builder()
                .contoId(savedBankAccount.getId())
                .iban(savedBankAccount.getIban())
                .amount(request.getAmount())
                .saldoAttuale(savedBankAccount.getSaldo())
                .message("Prelievo andato a buon fine")
                .build();
    }

    @Transactional(readOnly = true)
    public BigDecimal getSaldo(String accountId) {

        String userId = securityService.getCurrentUserId();
        log.info("[SALDO] Visualizza saldo per {}", userId);

        BankAccount bankAccount = bankAccountRepository.findByIdAndUserId(accountId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Conto non trovato per questo utente"));

        return bankAccount.getSaldo();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public BonificoResponseDto bonifico(@Valid BonificoRequestDto request) {
        String userId = securityService.getCurrentUserId();

        log.info("[BONIFICO] Boifico in esecuzione per {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User non trovato"));

        if (!securityService.verifyPassword(user, request.getPassword())) {
            throw new PasswordNotValidEx("Password errata");
        }

        BankAccount bankAccount = bankAccountRepository.findByIdAndUserId(request.getBankAccountId(), userId)
                .orElseThrow(() -> new EntityNotFoundException("Conto non trovato per questo utente"));

        if (bankAccount.getSaldo().compareTo(request.getAmount()) < 0){
            throw new SaldoNonDisponibileEx("Saldo insufficente, inserisci una somma minore o deposita");
        }

        //fase prelievo
        bankAccount.setSaldo(bankAccount.getSaldo().subtract(request.getAmount()));
        bankAccountRepository.save(bankAccount);
        Bonifico bonificoEseguito = Bonifico.builder()
                .ibanDestinatario(request.getIbanDestinatario())
                .nomeDestinatario(request.getNomeDestinatario())
                .cognomeDestinatario(request.getCognomeDestinatario())
                .motivation(request.getMotivation())
                .amount(request.getAmount())
                .fromAccount(bankAccount)
                .type(TransactionType.BONIFICO_INVIATO)
                .date(LocalDateTime.now())
                .build();
        transactionsRepository.save(bonificoEseguito);

        //fase deposito
        BankAccount accountDestinatario = bankAccountRepository.findByIban(request.getIbanDestinatario())
                .orElseThrow(() -> new EntityNotFoundException("Conto non trovato per questo utente"));
        accountDestinatario.setSaldo(accountDestinatario.getSaldo().add(request.getAmount()));
        Transaction bonificoRicevuto = Transaction.builder()
                .motivation(request.getMotivation())
                .amount(request.getAmount())
                .fromAccount(bankAccount)
                .type(TransactionType.BONIFICO_RICEVUTO)
                .date(LocalDateTime.now())
                .build();

        transactionsRepository.save(bonificoRicevuto);

        /*Invio Email disabilitato
        emailSenderService.sendEmail(user.getEmail(), String.format(
                "Bonifico eseguito sul tuo conto %S al conto di %S di %,.2f %s",
                bankAccount.getIban(), accountDestinatario.getIban(), request.getAmount(), bankAccount.getValuta()));
         */
        log.info("[BONIFICO] Bonifico eseguito da {} di {}", user.getEmail(), request.getAmount());

        return BonificoResponseDto.builder()
                .id(bonificoEseguito.getId())
                .motivation(bonificoEseguito.getMotivation())
                .amount(request.getAmount())
                .bankAccountId(bankAccount.getId())
                .type(TransactionType.BONIFICO_INVIATO)
                .date(LocalDateTime.now())
                .ibanDestinatario(accountDestinatario.getIban())
                .nomeDestinatario(accountDestinatario.getFirstName())
                .cognomeDestinatario(accountDestinatario.getLastName())
                .build();
    }
}

