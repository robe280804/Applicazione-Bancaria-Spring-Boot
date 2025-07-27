package com.app_bancaria.my_bnl_application.dto;

import com.app_bancaria.my_bnl_application.model.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@Schema(name = "TransazioneResponseDto", description = "DTO per mostrare la transazione durante la visualizzazione di essa")
public class TransazioneResponseDto {

    @Schema(description = "ID univoco dell'conto bancario che ha eseguito la transazione", example = "f13a81a9-3c25-4c1a-bc1b-47f6a8e5e33d")
    private String contoId;

    @Schema(description = "Iban del conto bancario che ha eseguito la transazione")
    private String iban;

    @Schema(description = "Somma di denaro")
    private BigDecimal amount;

    @Schema(description = "Saldo attuale del conto bancario")
    private BigDecimal saldoAttuale;

    @Schema(description = "Motivazione della transazione")
    private String message;
}
