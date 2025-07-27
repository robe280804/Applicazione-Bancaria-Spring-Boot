package com.app_bancaria.my_bnl_application.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipologia di transazione", example = "PRELIEVO")
public enum TransactionType {
    PRELIEVO,
    DEPOSITO,
    BONIFICO_INVIATO,
    BONIFICO_RICEVUTO
}
