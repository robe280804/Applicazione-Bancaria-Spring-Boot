package com.app_bancaria.my_bnl_application.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ruolo assegnato all'utente", example = "USER")
public enum Role {
    USER,
    ADMIN
}
