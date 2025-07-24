package com.app_bancaria.my_bnl_application.controller;

import com.app_bancaria.my_bnl_application.dto.*;
import com.app_bancaria.my_bnl_application.exception.EmailAlreadyExistsEx;
import com.app_bancaria.my_bnl_application.exception.PasswordNotValidEx;
import com.app_bancaria.my_bnl_application.exception.SaldoNonDisponibileEx;
import com.app_bancaria.my_bnl_application.service.BankAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/bank-account")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "BankAccount", description = "gestione dell'account bancari degli utenti")
public class BankAccountController {


    private final BankAccountService bankAccountService;

    @Operation(
            summary = "Creazione  conto bancario",
            description = "Crea un nuovo conto bancario per un determinato utente, attraverso i dati forniti nel DTO di richiesta. " +
                    "Se l'utente attraverso l'id non viene trovato nel sistema, restituisce un errore 404.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Conto  bancario creato con successo",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BankAccountResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dati di input non validi",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MethodArgumentNotValidException.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Utente non trovato",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UsernameNotFoundException.class)
                            )
                    )
            },
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<BankAccountResponseDto> create(@RequestBody @Valid BankAccountRequestDto request){
        log.info("controller raggiunto");
        return ResponseEntity.ok(bankAccountService.create(request));
    }


    @Operation(
            summary = "Visualizzazione conti bancari utente",
            description = "Visualizza i conti bancari in possesso da un utente, attraverso il token fornito si ottiene l'utente. " +
                    "I conti bancari vengono trasformati in DTO di risposta con le informazioni necessarie." +
                    "Se l'utente attraverso l'id non viene trovato nel sistema, restituisce un errore 404. ",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Conti bancari trovati e restituiti con successo",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BankAccountResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Utente non trovato",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UsernameNotFoundException.class)
                            )
                    )
            },
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/")
    public ResponseEntity<List<BankAccountResponseDto>> getUserBankAccount(){
        return ResponseEntity.ok(bankAccountService.getUserBankAccount());
    }


    @Operation(
            summary = "Prelievo conto bancario",
            description = "Preleva  una somma di denaro in un preciso conto bancario appartenente all'utente attraverso il DTO di richiesta. " +
                    "Se l'utente attraverso l'id non viene trovato nel sistema, restituisce un errore 404." +
                    "Se l'account bancario ottenuto attraverso l'id nel DTO della richiesta non esiste, restituisce un errore 404. " +
                    "Prima di depositare controlla che la password passata nel DTO sia uguale a quella salvata nel database, inerente all'utente. ",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Deposito andato a buon fine",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TransazioneResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dati di input non validi",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MethodArgumentNotValidException.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Password non valida",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PasswordNotValidEx.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Utente non trovato",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UsernameNotFoundException.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Conto bancario non trovato",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EntityNotFoundException.class)
                            )
                    ),
            },
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/deposita")
    public ResponseEntity<TransazioneResponseDto> deposita(@RequestBody @Valid TransazioneRequestDto request){
        return ResponseEntity.ok(bankAccountService.deposita(request));
    }

    @Operation(
            summary = "Prelievo conto bancario",
            description = "Preleva  una somma di denaro in un preciso conto bancario appartenente all'utente attraverso il DTO di richiesta. " +
                    "Se l'utente attraverso l'id non viene trovato nel sistema, restituisce un errore 404. " +
                    "Se l'account bancario ottenuto attraverso l'id nel DTO della richiesta non esiste, restituisce un errore 404. " +
                    "Prima di depositare controlla che la password passata nel DTO sia uguale a quella salvata nel database, inerente all'utente. " +
                    "Se la somma da prelevare non è disponibile nel conto bancario, restituendo un 422.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Prelievo andato a buon fine",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TransazioneResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dati di input non validi",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MethodArgumentNotValidException.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Password non valida",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PasswordNotValidEx.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Utente non trovato",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UsernameNotFoundException.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Conto bancario non trovato",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EntityNotFoundException.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Saldo non disponibile",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SaldoNonDisponibileEx.class)
                            )
                    )
            },
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/preleva")
    public ResponseEntity<TransazioneResponseDto> preleva(@RequestBody @Valid TransazioneRequestDto request){
        return ResponseEntity.ok(bankAccountService.preleva(request));
    }

    @Operation(
            summary = "Bonifico conto bancario",
            description = "Esegue un bonifico dal DTO di richiesta passato come parametro. " +
                    "Se l'utente attraverso l'id non viene trovato nel sistema, restituisce un errore 404. " +
                    "Se l'account bancario ottenuto attraverso l'id nel DTO della richiesta non esiste, restituisce un errore 404. " +
                    "Prima di depositare controlla che la password passata nel DTO sia uguale a quella salvata nel database, inerente all'utente. " +
                    "Se la somma da prelevare non è disponibile nel conto bancario, restituendo un 422. " +
                    "Se il conto bancario ottenuto attraverso l'iban non esiste, restituisce un errore 404. ",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Bonifico andato a buon fine",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BonificoResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dati di input non validi",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MethodArgumentNotValidException.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Password non valida",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PasswordNotValidEx.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Utente non trovato",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UsernameNotFoundException.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Conto bancario non trovato",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EntityNotFoundException.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Saldo non disponibile",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SaldoNonDisponibileEx.class)
                            )
                    )
            },
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/bonifico")
    public ResponseEntity<BonificoResponseDto> bonifico(@RequestBody @Valid BonificoRequestDto request){
        return ResponseEntity.ok(bankAccountService.bonifico(request));
    }

    @Operation(
            summary = "Visualizzazione saldo conto bancario utente",
            description = "Visualizza il saldo del conto bancario in possesso da un utente, attraverso il token fornito si ottiene l'utente. " +
                    "Il conto bancario viene ottenuto attraverso l'id presente nel DTO della richiesta, se non trovato restituisce un errore 404. " +
                    "Se l'utente attraverso l'id non viene trovato nel sistema, restituisce un errore 404. ",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Saldo visualizzato con successo",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BigDecimal.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Utente o Conto bancario non trovato",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UsernameNotFoundException.class)
                            )
                    )
            },
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/saldo/{idAccount}")
    public ResponseEntity<BigDecimal> getSaldo(@PathVariable String idAccount){
        return ResponseEntity.ok(bankAccountService.getSaldo(idAccount));
    }
}
