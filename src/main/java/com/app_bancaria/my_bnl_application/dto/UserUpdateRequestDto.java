package com.app_bancaria.my_bnl_application.dto;

import com.app_bancaria.my_bnl_application.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@Schema(name = "UserUpdateRequestDo", description = "DTO per la richiesta della modifica di un User, usato in un controller admin")
public class UserUpdateRequestDto {

    @Email(message = "Email non valida")
    @NotBlank(message = "L'email non può essere vuota")
    @Schema(description = "email dell'utente", example = "mario.rossi@example.com")
    private String email;

    @NotNull(message = "La password non può essere vuota")
    @Size(min = 6, message = "La password deve essere lunga almeno 6 caratteri")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$",
            message = "La password deve contenere almeno una maiuscola, una minuscola, un numero e un carattere speciale")
    @Schema(description = "Password dell'utente", example = "Password*123!")
    private String password;

    @Schema(description = "Ruoli dell'utente", example = "[USER]")
    private Set<Role> role;
}
