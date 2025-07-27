package com.app_bancaria.my_bnl_application.dto;

import com.app_bancaria.my_bnl_application.model.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Schema(name = "TransactionsRequestDto", description = "DTO per la richiesta delle transazioni del conto bancario")
public class TransactionsRequestDto {

    @NotBlank(message = "L'idAccount non può essere vuoto")
    @NotBlank(message = "L'idAccount non può essere vuoto")
    @Pattern(
            regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$",
            message = "L'idAccount deve essere un UUID valido"
    )
    @Schema(description = "ID univoco dell'conto bancario", example = "f13a81a9-3c25-4c1a-bc1b-47f6a8e5e33d")
    private String idAccount;

    @NotNull(message = "Il tipo di transazione è obbligatorio")
    @Schema(description = "Tipologia di transazione", example = "DEPOSITO")
    private TransactionType transactionType;

}
