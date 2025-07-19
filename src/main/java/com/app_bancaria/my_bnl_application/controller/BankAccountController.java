package com.app_bancaria.my_bnl_application.controller;

import com.app_bancaria.my_bnl_application.dto.BankAccountRequestDto;
import com.app_bancaria.my_bnl_application.dto.BankAccountResponseDto;
import com.app_bancaria.my_bnl_application.service.BankAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bank-account")
@RequiredArgsConstructor
@Slf4j
public class BankAccountController {

    //create
    //get user bank-acc
    //deposita
    //prelieva
    //get saldo

    private final BankAccountService bankAccountService;

    @PostMapping("/create")
    public ResponseEntity<BankAccountResponseDto> create(@RequestBody @Valid BankAccountRequestDto request){
        log.info("controller raggiunto");
        return ResponseEntity.ok(bankAccountService.create(request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<BankAccountResponseDto>> getUserBankAccount(@PathVariable String userId){
        return  ResponseEntity.ok(bankAccountService.getUserBankAccount(userId));
    }
}
