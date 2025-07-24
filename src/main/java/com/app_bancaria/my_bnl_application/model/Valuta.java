package com.app_bancaria.my_bnl_application.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Valuta assegnata al conto corrente", example = "EUR")
public enum Valuta {
    EUR,
    USD,
    GBP,
    CHF,
    JPY
}
