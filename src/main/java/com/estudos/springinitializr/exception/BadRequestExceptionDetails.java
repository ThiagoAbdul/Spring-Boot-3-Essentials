package com.estudos.springinitializr.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class BadRequestExceptionDetails {
    private String error;
    private int status;
    private String message;
    private String developerMessage;
    private LocalDateTime timestamp;
}
