package com.app_bancaria.my_bnl_application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@Schema(name = "TransazioneRequestDto", description = "DTO per eseguire una transazione")
public class TransazioneRequestDto {

    @NotBlank(message = "L'ID del conto è obbligatorio")
    @Pattern(
            regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$",
            message = "L'idAccount deve essere un UUID valido"
    )
    @Schema(description = "ID univoco dell'conto bancario che ha eseguito la transazione", example = "f13a81a9-3c25-4c1a-bc1b-47f6a8e5e33d")
    private String accountId;

    @NotNull(message = "L'importo è obbligatorio")
    @DecimalMin(value = "0.01", inclusive = true, message = "L'importo deve essere maggiore di zero")
    @Schema(description = "Somma di denaro")
    private BigDecimal amount;

    @NotNull(message = "La password non può essere vuota")
    @Size(min = 6, message = "La password deve essere lunga almeno 6 caratteri")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$",
            message = "La password deve contenere almeno una maiuscola, una minuscola, un numero e un carattere speciale")
    @Schema(description = "Password dell'utente per confermare la transazione", example = "Password*123!")
    private String password;

    @NotBlank(message = "La motivazione è obbligatoria")
    @Size(max = 255, message = "La motivazione non può superare i 255 caratteri")
    @Schema(description = "Motivazione della transazione")
    private String motivation;

}
