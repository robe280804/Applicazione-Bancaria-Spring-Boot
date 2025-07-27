package com.app_bancaria.my_bnl_application.controller.admin;

import com.app_bancaria.my_bnl_application.dto.UserResponseDto;
import com.app_bancaria.my_bnl_application.dto.UserUpdateRequestDto;
import com.app_bancaria.my_bnl_application.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminService adminService;

    //VISUALIZZARE TUTTI GLI UTENTI / MODIFICARLI / ELIMINARLI

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<UserResponseDto>> getUser(){
        return  ResponseEntity.ok(adminService.getUsers());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable String id,
                                                  @RequestBody @Valid UserUpdateRequestDto request){
        return ResponseEntity.ok(adminService.update(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id){
        adminService.delete(id);
        return  ResponseEntity.ok().build();
    }
}
