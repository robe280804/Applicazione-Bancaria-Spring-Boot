package com.app_bancaria.my_bnl_application.controller;

import com.app_bancaria.my_bnl_application.dto.*;
import com.app_bancaria.my_bnl_application.service.BankAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<BankAccountResponseDto> create(@RequestBody @Valid BankAccountRequestDto request){
        log.info("controller raggiunto");
        return ResponseEntity.ok(bankAccountService.create(request));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/")
    public ResponseEntity<List<BankAccountResponseDto>> getUserBankAccount(){
        return ResponseEntity.ok(bankAccountService.getUserBankAccount());
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/deposita")
    public ResponseEntity<TransazioneResponseDto> deposita(@RequestBody @Valid TransazioneRequestDto request){
        return ResponseEntity.ok(bankAccountService.deposita(request));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/preleva")
    public ResponseEntity<TransazioneResponseDto> preleva(@RequestBody @Valid TransazioneRequestDto request){
        return ResponseEntity.ok(bankAccountService.preleva(request));
    }

    @PreAuthorize("hasRole('USER)")
    @PostMapping("/bonifico")
    public ResponseEntity<BonificoResponseDto> bonifico(@RequestBody @Valid BonificoRequestDto request){
        return ResponseEntity.ok(bankAccountService.bonifico(request));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/saldo/{idAccount}")
    public ResponseEntity<BigDecimal> getSaldo(@PathVariable String idAccount){
        return ResponseEntity.ok(bankAccountService.getSaldo(idAccount));
    }


}
