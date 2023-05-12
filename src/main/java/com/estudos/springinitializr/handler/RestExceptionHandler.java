package com.estudos.springinitializr.handler;

import com.estudos.springinitializr.exception.BadRequestException;
import com.estudos.springinitializr.exception.BadRequestExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException e){
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .error("Bad request exception")
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(e.getMessage())
                        .developerMessage(e.getClass().getName())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }
}
