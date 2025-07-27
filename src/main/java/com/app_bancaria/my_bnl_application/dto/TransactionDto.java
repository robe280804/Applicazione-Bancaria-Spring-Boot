package com.app_bancaria.my_bnl_application.dto;

import com.app_bancaria.my_bnl_application.model.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@Schema(name = "TransactionDto", description = "DTO per la risposta per la visualizzazione delle transazioni")
public class TransactionDto {

    @Schema(description = "Id della transazione")
    private Long id;

    @Schema(description = "Motivazione della transazione")
    private String motivation;

    @Schema(description = "Importo della transazione")
    private BigDecimal amount;

    @Schema(description = "Id del conto bancario a cui è stata eseguita la transazione")
    private String bankAccountId;

    @Schema(description = "Tipologia di transazione", example = "DEPOSITO")
    private TransactionType type;

    @Schema(description = "Data in cui è avvenuta la transazione", example = "2025-07-24T15:45:32")
    private LocalDateTime date;
}
