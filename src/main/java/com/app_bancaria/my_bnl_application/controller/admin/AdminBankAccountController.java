package com.app_bancaria.my_bnl_application.controller.admin;

import com.app_bancaria.my_bnl_application.dto.BankAccountRequestDto;
import com.app_bancaria.my_bnl_application.dto.BankAccountResponseDto;
import com.app_bancaria.my_bnl_application.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/bank-account")
@RequiredArgsConstructor
public class AdminBankAccountController {

    private final AdminService adminService;

    //VISUALIZZARE TUTTI I CONTI / MODIFICARLI / ELIMINARLI

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<BankAccountResponseDto>> getBankAccounts(){
        return ResponseEntity.ok(adminService.getBankAccounts());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/modify/{id}")
    public ResponseEntity<BankAccountResponseDto> update(@PathVariable String id,
                                                         @RequestBody @Valid BankAccountRequestDto request){
        return ResponseEntity.ok(adminService.updateAccount(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        adminService.deleteAccount(id);
        return ResponseEntity.ok().build();
    }
}
