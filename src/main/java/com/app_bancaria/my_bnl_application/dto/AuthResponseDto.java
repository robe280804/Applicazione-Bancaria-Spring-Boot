package com.app_bancaria.my_bnl_application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponseDto {

    private String id;
    private String email;
    private String message;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AuthResponseDto(String id,String email, String message, LocalDateTime createdAt, LocalDateTime updatedAt){
        this.email = email;
        this.message = message;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.token = null;
    }
}
