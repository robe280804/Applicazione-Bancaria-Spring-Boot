package com.app_bancaria.my_bnl_application.controller;

import com.app_bancaria.my_bnl_application.dto.AuthRequestDto;
import com.app_bancaria.my_bnl_application.dto.AuthResponseDto;
import com.app_bancaria.my_bnl_application.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody @Valid AuthRequestDto request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid AuthRequestDto request){
        return ResponseEntity.ok(authService.login(request));
    }
}
