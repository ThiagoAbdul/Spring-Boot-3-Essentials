package com.estudos.springframework.exceptions;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class ExceptionDetails {
    protected String error;
    protected final int status;
    protected String message;
    protected String developerMessage;
    protected LocalDateTime timestamp = LocalDateTime.now();


}
