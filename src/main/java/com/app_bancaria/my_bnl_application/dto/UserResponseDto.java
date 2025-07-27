package com.app_bancaria.my_bnl_application.dto;

import com.app_bancaria.my_bnl_application.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class UserResponseDto {

    @Schema(description = "ID univoco dell'Utente", example = "f13a81a9-3c25-4c1a-bc1b-47f6a8e5e33d")
    private String id;

    @Schema(description = "email dell'utente", example = "mario.rossi@example.com")
    private String email;

    @Schema(description = "Ruoli dell'utente", example = "[USER]")
    private Set<Role> role;

    @Schema(description = "Data e ora di creazione dell'utente", example = "2025-07-24T15:45:32")
    private LocalDateTime createdAt;

    @Schema(description = "Data e ora dell'ultimo aggiornamento del utente", example = "2025-07-24T15:45:32")
    private LocalDateTime updateAt;
}
