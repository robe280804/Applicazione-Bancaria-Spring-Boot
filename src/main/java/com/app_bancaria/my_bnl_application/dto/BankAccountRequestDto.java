package com.app_bancaria.my_bnl_application.dto;

import com.app_bancaria.my_bnl_application.model.TipoConto;
import com.app_bancaria.my_bnl_application.model.Valuta;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder

@Schema(name = "BankAccountRequestDto", description = "DTO per la richiesta della creazione del conto bancario")
public class BankAccountRequestDto {

    @NotBlank(message = "Il nome è obbligatorio")
    @Size(min = 2, max = 50, message = "Il nome deve contenere tra 2 e 50 caratteri")
    @Schema(description = "nome dell'utente a cui apparterrà il conto", example = "Roberto")
    private String firstName;

    @NotBlank(message = "Il cognome è obbligatorio")
    @Size(min = 2, max = 50, message = "Il cognome deve contenere tra 2 e 50 caratteri")
    @Schema(description = "cognome dell'utente a cui apparterrà il conto", example = "Sodini")
    private String lastName;

    @NotBlank(message = "Il codice fiscale è obbligatorio")
    @Pattern(regexp = "^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$",
            message = "Il codice fiscale non è valido")
    @Schema(description = "codice fiscal dell'utente a cui apparterrà il conto", example = "SDNRRT08M23H987Q")
    private String codiceFiscale;

    @NotNull(message = "Il saldo iniziale è obbligatorio")
    @DecimalMin(value = "0.00", inclusive = true, message = "Il saldo non può essere negativo")
    @Schema(description = "Saldo iniziale presente sul conto", defaultValue = "0.00")
    private BigDecimal saldo;

    @NotNull(message = "Il tipo di conto è obbligatorio")
    @Schema(description = "Tipologia di conto corrente", example = "RISPARMIO")
    private TipoConto type;

    @NotNull(message = "La valuta è obbligatoria")
    @Schema(description = "Valuta del conto corrente", example = "EUR")
    private Valuta valuta;
}

