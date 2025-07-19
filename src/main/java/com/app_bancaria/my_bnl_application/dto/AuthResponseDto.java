package com.app_bancaria.my_bnl_application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponseDto {

    private String email;
    private String message;
    private String token;

    public AuthResponseDto(String email, String message){
        this.email = email;
        this.message = message;
        this.token = null;
    }
}
