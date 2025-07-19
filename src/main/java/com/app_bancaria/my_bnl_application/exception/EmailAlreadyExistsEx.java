package com.app_bancaria.my_bnl_application.exception;

public class EmailAlreadyExistsEx extends RuntimeException {
    public EmailAlreadyExistsEx(String message) {
        super(message);
    }
}
