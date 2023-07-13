package com.estudos.springframework.exceptions;

import org.springframework.security.core.AuthenticationException;

public class EmailAlreadyRegisteredException extends Exception {
    public EmailAlreadyRegisteredException(String email) {
        super("E-mail already registered: " + email);
    }
}
