package com.app_bancaria.my_bnl_application.dto;

import com.app_bancaria.my_bnl_application.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class UserResponseDto {

    private String id;
    private String email;
    private Set<Role> role;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}
