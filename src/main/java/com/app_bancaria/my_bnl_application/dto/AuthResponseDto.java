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
    private LocalDateTime updateAt;

    public AuthResponseDto(String id,String email, String message, LocalDateTime createdAt, LocalDateTime updateAt){
        this.email = email;
        this.message = message;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.token = null;
    }
}
