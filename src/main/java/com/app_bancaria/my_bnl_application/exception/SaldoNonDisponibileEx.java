package com.app_bancaria.my_bnl_application.exception;

public class SaldoNonDisponibileEx extends RuntimeException {
    public SaldoNonDisponibileEx(String message) {
        super(message);
    }
}
