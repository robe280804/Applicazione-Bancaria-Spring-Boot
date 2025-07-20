package com.app_bancaria.my_bnl_application.dto;

import com.app_bancaria.my_bnl_application.model.TipoConto;
import com.app_bancaria.my_bnl_application.model.Valuta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class BankAccountResponseDto {

    private String id;
    private String userId;
    private String firstName;
    private String lastName;
    private String codiceFiscale;
    private String iban;
    private String numeroConto;
    private BigDecimal saldo;
    private TipoConto type;
    private Valuta valuta;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String message;

}
