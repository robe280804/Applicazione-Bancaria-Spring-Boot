package com.app_bancaria.my_bnl_application.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class BonificoRequestDto {

    @NotNull(message = "La password non può essere vuota")
    @Size(min = 6, message = "La password deve essere lunga almeno 6 caratteri")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$",
            message = "La password deve contenere almeno una maiuscola, una minuscola, un numero e un carattere speciale")
    private String password;

    @NotBlank(message = "La motivazione è obbligatoria")
    @Size(max = 255, message = "La motivazione non può superare i 255 caratteri")
    private String motivation;

    @NotNull(message = "L'importo è obbligatorio")
    @DecimalMin(value = "0.01", inclusive = true, message = "L'importo deve essere maggiore di zero")
    private BigDecimal amount;

    @NotBlank(message = "L'idAccount non può essere vuoto")
    @Pattern(
            regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$",
            message = "L'idAccount deve essere un UUID valido"
    )
    private String bankAccountId;

    @NotBlank(message = "IBAN destinatario non può essere vuoto")
    @Pattern(regexp = "[A-Z]{2}\\d{2}[A-Z0-9]{1,30}", message = "IBAN destinatario non valido")
    private String ibanDestinatario;

    @NotBlank(message = "Il nome è obbligatorio")
    @Size(min = 2, max = 50, message = "Il nome deve contenere tra 2 e 50 caratteri")
    private String nomeDestinatario;

    @NotBlank(message = "Il cognome è obbligatorio")
    @Size(min = 2, max = 50, message = "Il cognome deve contenere tra 2 e 50 caratteri")
    private String cognomeDestinatario;
}
