package com.app_bancaria.my_bnl_application.controller;

import com.app_bancaria.my_bnl_application.dto.BankAccountRequestDto;
import com.app_bancaria.my_bnl_application.dto.BankAccountResponseDto;
import com.app_bancaria.my_bnl_application.service.BankAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bank-account")
@RequiredArgsConstructor
public class BankAccountController {

    //create
    //get user bank-acc
    //deposita
    //prelieva
    //get saldo

    private final BankAccountService bankAccountService;

    @PostMapping("/create")
    public ResponseEntity<BankAccountResponseDto> create(@RequestBody @Valid BankAccountRequestDto request){
        return ResponseEntity.ok(bankAccountService.create(request));
    }
}
