package com.estudos.springframework.handler;


import com.estudos.springframework.exceptions.BadRequestException;
import com.estudos.springframework.exceptions.BadRequestExceptionDetails;
import com.estudos.springframework.exceptions.MethodArgumentNotValidExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler /*extends ResponseEntityExceptionHandler*/ {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException e){
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .error("Bad request exception")
                        .message(e.getMessage())
                        .developerMessage(e.getClass().getName())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MethodArgumentNotValidExceptionDetails> handlerMethodArgumentNotValidException(
                                                    MethodArgumentNotValidException e){

        return new ResponseEntity<>(
                MethodArgumentNotValidExceptionDetails.builder(HttpStatus.BAD_REQUEST.value(), e)
                        .error("Bad request exception")
                        .message(e.getMessage())
                        .developerMessage(e.getClass().getName())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }
}
