package com.app_bancaria.my_bnl_application.dto;

import com.app_bancaria.my_bnl_application.model.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class TransactionsRequestDto {

    @NotBlank(message = "L'idAccount non può essere vuoto")
    @NotBlank(message = "L'idAccount non può essere vuoto")
    @Pattern(
            regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$",
            message = "L'idAccount deve essere un UUID valido"
    )
    private String idAccount;

    @NotNull(message = "Il tipo di transazione è obbligatorio")
    private TransactionType transactionType;

}
