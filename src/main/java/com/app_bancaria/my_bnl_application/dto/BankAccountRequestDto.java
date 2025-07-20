package com.app_bancaria.my_bnl_application.dto;

import com.app_bancaria.my_bnl_application.model.TipoConto;
import com.app_bancaria.my_bnl_application.model.Valuta;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class BankAccountRequestDto {

    @NotBlank(message = "Il nome è obbligatorio")
    @Size(min = 2, max = 50, message = "Il nome deve contenere tra 2 e 50 caratteri")
    private String firstName;

    @NotBlank(message = "Il cognome è obbligatorio")
    @Size(min = 2, max = 50, message = "Il cognome deve contenere tra 2 e 50 caratteri")
    private String lastName;

    @NotBlank(message = "Il codice fiscale è obbligatorio")
    @Pattern(regexp = "^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$",
            message = "Il codice fiscale non è valido")
    private String codiceFiscale;

    @NotNull(message = "Il saldo iniziale è obbligatorio")
    @DecimalMin(value = "0.00", inclusive = true, message = "Il saldo non può essere negativo")
    private BigDecimal saldo;

    @NotNull(message = "Il tipo di conto è obbligatorio")
    private TipoConto type;

    @NotNull(message = "La valuta è obbligatoria")
    private Valuta valuta;
}

