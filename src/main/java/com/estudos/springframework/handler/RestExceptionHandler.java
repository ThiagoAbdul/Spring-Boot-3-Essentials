package com.estudos.springframework.handler;


import com.estudos.springframework.exceptions.BadRequestException;
import com.estudos.springframework.exceptions.BadRequestExceptionDetails;
import com.estudos.springframework.exceptions.ForbbidenExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler /*extends ResponseEntityExceptionHandler*/ {

    @ExceptionHandler(BadRequestException.class)
    ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BadRequestExceptionDetails(e));
    }

    @ExceptionHandler(AuthenticationException.class)
    ResponseEntity<ForbbidenExceptionDetails> handlerForbbidenException(AuthenticationException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ForbbidenExceptionDetails(e));
    }

}
