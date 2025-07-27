package com.app_bancaria.my_bnl_application.controller;

import com.app_bancaria.my_bnl_application.dto.BankAccountResponseDto;
import com.app_bancaria.my_bnl_application.dto.TransactionDto;
import com.app_bancaria.my_bnl_application.dto.TransactionsRequestDto;
import com.app_bancaria.my_bnl_application.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
@Tag(name = "TransactionController", description = "gestione delle transazioni di un determinato conto bancario appartenente ad un utenti")
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(
            summary = "Visualizzazione transazioni del conto bancario",
            description = "Visualizza le transazioni appartenenti ad un conto bancario di un utente" +
                    "attraverso l'id dell'account passato come variabile nel path. " +
                    "Se l'utente non è già presente nel sistema, restituisce un errore 404. " +
                    "Se l'account bancario non esiste restituisce un errore 404. ",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Transazioni trovate e restituite con successo",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TransactionDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Conto bancario non trovato",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EntityNotFoundException.class)
                            )
                    )
            },
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{accountId}")
    public ResponseEntity<List<TransactionDto>> getBankAccountTransaction(@PathVariable String accountId){
        return ResponseEntity.ok(transactionService.getTransactions(accountId));
    }

    @Operation(
            summary = "Visualizzazione transazioni filtrate del conto bancario",
            description = "Visualizza le transazioni appartenenti ad un conto bancario di un utente in base alla tipologia " +
                    "passata nel DTO della richiesta. " +
                    "Se l'utente non è già presente nel sistema, restituisce un errore 404. " +
                    "Se l'account bancario non esiste restituisce un errore 404. ",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Transazioni trovate e restituite con successo",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TransactionDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Conto bancario non trovato",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EntityNotFoundException.class)
                            )
                    )
            },
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/byType")
    public ResponseEntity<List<TransactionDto>> getTransactionsByType(@RequestBody @Valid TransactionsRequestDto request){
        return ResponseEntity.ok(transactionService.getTransactionsByType(request));
    }
}
