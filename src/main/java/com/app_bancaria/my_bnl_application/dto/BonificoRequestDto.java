package com.app_bancaria.my_bnl_application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@Schema(name = "BonificoRequestDto", description = "DTO per la richiestadi bonifico")
public class BonificoRequestDto {

    @NotNull(message = "La password non può essere vuota")
    @Size(min = 6, message = "La password deve essere lunga almeno 6 caratteri")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$",
            message = "La password deve contenere almeno una maiuscola, una minuscola, un numero e un carattere speciale")
    @Schema(description = "password dell'utente per confermare la transazione")
    private String password;

    @NotBlank(message = "La motivazione è obbligatoria")
    @Size(max = 255, message = "La motivazione non può superare i 255 caratteri")
    @Schema(description = "motivazione del bonifico")
    private String motivation;

    @NotNull(message = "L'importo è obbligatorio")
    @DecimalMin(value = "0.01", inclusive = true, message = "L'importo deve essere maggiore di zero")
    @Schema(description = "Somma di denaro del bonifico")
    private BigDecimal amount;

    @NotBlank(message = "L'idAccount non può essere vuoto")
    @Pattern(
            regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$",
            message = "L'idAccount deve essere un UUID valido"
    )
    @Schema(description = "Id dell'account bancario dell'intestatario")
    private String bankAccountId;

    @NotBlank(message = "IBAN destinatario non può essere vuoto")
    @Pattern(regexp = "[A-Z]{2}\\d{2}[A-Z0-9]{1,30}", message = "IBAN destinatario non valido")
    @Schema(description = "Iban dell'account bancario del destinatario")
    private String ibanDestinatario;

    @NotBlank(message = "Il nome è obbligatorio")
    @Size(min = 2, max = 50, message = "Il nome deve contenere tra 2 e 50 caratteri")
    @Schema(description = "Nome del destinatario")
    private String nomeDestinatario;

    @NotBlank(message = "Il cognome è obbligatorio")
    @Size(min = 2, max = 50, message = "Il cognome deve contenere tra 2 e 50 caratteri")
    @Schema(description = "Cognome del destinatario")
    private String cognomeDestinatario;
}
