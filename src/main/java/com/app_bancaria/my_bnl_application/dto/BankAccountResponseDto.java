package com.app_bancaria.my_bnl_application.dto;

import com.app_bancaria.my_bnl_application.model.TipoConto;
import com.app_bancaria.my_bnl_application.model.Valuta;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class BankAccountResponseDto {

    @Schema(description = "ID univoco dell'conto bancario", example = "f13a81a9-3c25-4c1a-bc1b-47f6a8e5e33d")
    private String id;

    @Schema(description = "ID univoco dell'utente", example = "f13a81a9-3c25-4c1a-bc1b-47f6a8e5e33d")
    private String userId;

    @Schema(description = "Nome dell'utente inerente al conto bancario")
    private String firstName;

    @Schema(description = "Cognome dell'utente inerente al conto bancario")
    private String lastName;

    @Schema(description = "Codice fiscale dell'utente inerente al conto bancario")
    private String codiceFiscale;

    @Schema(description = "Iban del conto bancario generato automaticamente prima della creazione")
    private String iban;

    @Schema(description = "Numero del conto bancario generato automaticamente prima della creazione")
    private String numeroConto;

    @Schema(description = "Saldo presente nel conto bancario")
    private BigDecimal saldo;

    @Schema(description = "Tipologia di conto bancario", example = "RISPARMIO")
    private TipoConto type;

    @Schema(description = "Valuta del conto bancario", example = "EUR")
    private Valuta valuta;

    @Schema(description = "Data e ora di creazione del conto", example = "2025-07-24T15:45:32")
    private LocalDateTime createdAt;

    @Schema(description = "Data e ora dell'ultimo aggiornamento del conto", example = "2025-07-24T15:45:32")
    private LocalDateTime updatedAt;

    @Schema(description = "Messaggio di approvazione della creazione del conto")
    private String message;

}
