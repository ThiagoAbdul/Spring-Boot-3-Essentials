package com.estudos.springframework.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ExceptionDetails {
    protected String error;
    protected int status;
    protected String message;

    protected ExceptionDetails(Exception e, HttpStatus httpStatus) {
        this.error = e.getClass().getName();
        this.status = httpStatus.value();
        this.message = e.getMessage();
    }

    protected LocalDateTime timestamp = LocalDateTime.now();


}
