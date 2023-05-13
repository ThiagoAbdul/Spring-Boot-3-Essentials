package com.estudos.springframework.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


@Getter
public class BadRequestExceptionDetails extends ExceptionDetails{
    private BadRequestExceptionDetails() {
        super(HttpStatus.BAD_REQUEST.value());
    }

    public static BadRequestExceptionDetailsBuilder builder(){
        return new BadRequestExceptionDetailsBuilder();
    }

    public static class BadRequestExceptionDetailsBuilder{
        private final BadRequestExceptionDetails exceptionDetails;

        private BadRequestExceptionDetailsBuilder(){
            this.exceptionDetails = new BadRequestExceptionDetails();
        }

        public BadRequestExceptionDetailsBuilder error(String error){
            exceptionDetails.error = error;
            return this;
        }

        public BadRequestExceptionDetailsBuilder message(String message){
            exceptionDetails.message = message;
            return this;
        }


        public BadRequestExceptionDetailsBuilder developerMessage(String developerMessage){
            exceptionDetails.developerMessage = developerMessage;
            return this;
        }


        public BadRequestExceptionDetailsBuilder timestamp(LocalDateTime timestamp){
            exceptionDetails.timestamp = timestamp;
            return this;
        }

        public BadRequestExceptionDetails build(){
            return this.exceptionDetails;
        }
    }
}
