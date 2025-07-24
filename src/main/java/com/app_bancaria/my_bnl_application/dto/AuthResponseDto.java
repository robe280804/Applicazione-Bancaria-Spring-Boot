package com.app_bancaria.my_bnl_application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@Schema(name = "AuthResponseDto", description = "DTO per la risposta all' autenticazione (registrazione/login)")
public class AuthResponseDto {

    @Schema(description = "ID univoco dell'utente", example = "f13a81a9-3c25-4c1a-bc1b-47f6a8e5e33d")
    private String id;

    @Schema(description = "Email dell'utente autenticato", example = "mario.rossi@example.com")
    private String email;

    @Schema(description = "Messaggio di conferma ", example = "Autenticazione avvenuta con successo")
    private String message;

    @Schema(description = "Token JWT generato per l'autenticazione", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @Schema(description = "Data e ora di creazione dell'account", example = "2025-07-24T15:45:32")
    private LocalDateTime createdAt;

    @Schema(description = "Data e ora dell'ultimo aggiornamento del profilo", example = "2025-07-24T15:45:32")
    private LocalDateTime updatedAt;


    //Costruttore parziale per la registrazione
    public AuthResponseDto(String id,String email, String message, LocalDateTime createdAt, LocalDateTime updatedAt){
        this.email = email;
        this.message = message;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.token = null;
    }
}
