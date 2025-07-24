package com.app_bancaria.my_bnl_application.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipologia di conto bancaio", example = "CORRENTE")
public enum TipoConto {
    CORRENTE,
    RISPARMIO,
    AZIENDALE,
    INVESTIMENTO,
    ALTRO
}
