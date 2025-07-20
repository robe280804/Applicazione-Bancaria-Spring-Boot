package com.app_bancaria.my_bnl_application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class TransazioneResponseDto {

    private String contoId;
    private String iban;
    private BigDecimal amount;
    private BigDecimal saldoAttuale;
    private String message;
}
