package com.app_bancaria.my_bnl_application.dto;

import com.app_bancaria.my_bnl_application.model.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@Schema(name = "BonificoResponseDo", description = "DTO per la risposta del bonifico")
public class BonificoResponseDto {

    @Schema(description = "Id del bonifico")
    private Long id;

    @Schema(description = "motivazione del bonifico")
    private String motivation;

    @Schema(description = "Somma di denaro del bonifico")
    private BigDecimal amount;

    @Schema(description = "Id dell'account bancario dell'intestatario")
    private String bankAccountId;

    @Schema(description = "tipologia di transazione", example = "BONIFICO INVIATO")
    private TransactionType type;

    @Schema(description = "Iban dell'account bancario del destinatario")private LocalDateTime date;
    private String ibanDestinatario;

    @Schema(description = "Nome del destinatario")
    private String nomeDestinatario;

    @Schema(description = "Cognome del destinatario")
    private String cognomeDestinatario;
}
