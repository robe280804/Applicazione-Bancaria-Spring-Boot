package com.app_bancaria.my_bnl_application.dto;

import com.app_bancaria.my_bnl_application.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class TransactionDto {

    private Long id;
    private String motivation;
    private BigDecimal amount;
    private String bankAccountId;
    private TransactionType type;
    private LocalDateTime date;
}
