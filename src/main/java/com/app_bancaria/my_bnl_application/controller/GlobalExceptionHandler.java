package com.app_bancaria.my_bnl_application.controller;

import com.app_bancaria.my_bnl_application.exception.EmailAlreadyExistsEx;
import com.app_bancaria.my_bnl_application.exception.PasswordNotValidEx;
import com.app_bancaria.my_bnl_application.exception.SaldoNonDisponibileEx;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsEx.class)
    public ResponseEntity<Object> handleEmailAlreadyExistsEx(EmailAlreadyExistsEx ex, WebRequest request){
        return generateResponse("Email già registrata", ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCCredentialsEx(EmailAlreadyExistsEx ex, WebRequest request){
        return generateResponse("Credenziali errate", ex, HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleArgumentNotValidEx(MethodArgumentNotValidException ex, WebRequest request){
        Map<String, Object> response = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                response.put(err.getField(), err.getDefaultMessage()));

        response.put("timestamps", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST);
        response.put("error", "Errore di validazione");
        response.put("message", ex.getMessage());
        response.put("path", extractPath(request));

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundEx(UsernameNotFoundException ex, WebRequest request){
        return generateResponse("Errore interno", ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(PasswordNotValidEx.class)
    public ResponseEntity<Object> handlePasswordNonValidEx(PasswordNotValidEx ex, WebRequest request){
        return generateResponse("Password errata", ex, HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(SaldoNonDisponibileEx.class)
    public ResponseEntity<Object> handleSaldoNonDispoEx(SaldoNonDisponibileEx ex, WebRequest request){
        return generateResponse("Saldo insufficiente", ex, HttpStatus.BAD_REQUEST, request);
    }

    private static ResponseEntity<Object> generateResponse(
            String error, Exception ex, HttpStatus status, WebRequest request
    ){
        Map<String, Object> response = new HashMap<>();
        response.put("timestamps", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", error);
        response.put("message", ex.getMessage());
        response.put("path", extractPath(request));

        return new ResponseEntity<>(response, status);
    }

    private static String extractPath(WebRequest request){
        return request.getDescription(false).replace("uri=", "");
    }
}
