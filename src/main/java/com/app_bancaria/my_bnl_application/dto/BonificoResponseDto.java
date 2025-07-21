package com.app_bancaria.my_bnl_application.dto;

import com.app_bancaria.my_bnl_application.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class BonificoResponseDto {
    private Long id;
    private String motivation;
    private BigDecimal amount;
    private String bankAccountId;
    private TransactionType type;
    private LocalDateTime date;
    private String ibanDestinatario;
    private String nomeDestinatario;
    private String cognomeDestinatario;
}
