package com.app_bancaria.my_bnl_application.controller;

import com.app_bancaria.my_bnl_application.dto.AuthRequestDto;
import com.app_bancaria.my_bnl_application.dto.AuthResponseDto;
import com.app_bancaria.my_bnl_application.exception.EmailAlreadyExistsEx;
import com.app_bancaria.my_bnl_application.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Gestione dell'autenticazione degli utenti")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Registra un nuovo utente",
            description = "Crea un nuovo utente nel sistema a partire dai dati forniti nel DTO di richiesta. " +
                    "Se l'utente è già registrato, restituisce un errore di conflitto.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Utente creato con successo",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dati di input non validi",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MethodArgumentNotValidException.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Utente già registrato",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmailAlreadyExistsEx.class)
                            )
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody @Valid AuthRequestDto request){
        return ResponseEntity.status(201).body(authService.register(request));   //ok(authService.register(request));
    }

    @Operation(
            summary = "Login  utente",
            description = "Accesso dell' utente nel sistema a partire dai dati forniti nel DTO di richiesta. " +
                    "Se l'utente non è già presente nel sistema, restituisce un errore not found.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Utente creato con successo",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dati di input non validi",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MethodArgumentNotValidException.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Credenziali errate per il login",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BadCredentialsException.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Utente non registrato",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UsernameNotFoundException.class)
                            )
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid AuthRequestDto request){
        return ResponseEntity.ok(authService.login(request));
    }
}
