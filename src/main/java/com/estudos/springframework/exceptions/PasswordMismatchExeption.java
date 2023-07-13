package com.estudos.springframework.exceptions;

import org.springframework.security.core.AuthenticationException;

public class PasswordMismatchExeption extends AuthenticationException {

    public PasswordMismatchExeption() {
        super("Incorrect password");
    }
}
