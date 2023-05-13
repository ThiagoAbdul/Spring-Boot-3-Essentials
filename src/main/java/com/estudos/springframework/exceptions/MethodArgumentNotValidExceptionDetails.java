package com.estudos.springframework.exceptions;

import lombok.Getter;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MethodArgumentNotValidExceptionDetails extends ExceptionDetails{
    private final String fields;
    private final String fieldsMessage;

    private MethodArgumentNotValidExceptionDetails(int status, MethodArgumentNotValidException e) {
        super(status);
        List<FieldError> listFieldsError = e.getBindingResult().getFieldErrors();
        this.fields = listFieldsError.stream()
                .map(FieldError::getField)
                .collect(Collectors.joining(", "));
        this.fieldsMessage = listFieldsError.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
    }

    public static MethodArgumentNotValidExceptionDetailsBuilder builder(
            int status, MethodArgumentNotValidException e){
        return new MethodArgumentNotValidExceptionDetailsBuilder(status, e);
    }

    public static class MethodArgumentNotValidExceptionDetailsBuilder{
        private final MethodArgumentNotValidExceptionDetails exceptionDetails;

        private MethodArgumentNotValidExceptionDetailsBuilder(int status, MethodArgumentNotValidException e){
            this.exceptionDetails = new MethodArgumentNotValidExceptionDetails(status, e);
        }

        public MethodArgumentNotValidExceptionDetailsBuilder error(String error){
            exceptionDetails.error = error;
            return this;
        }


        public MethodArgumentNotValidExceptionDetailsBuilder message(String message){
            exceptionDetails.message = message;
            return this;
        }


        public MethodArgumentNotValidExceptionDetailsBuilder developerMessage(String developerMessage){
            exceptionDetails.developerMessage = developerMessage;
            return this;
        }


        public MethodArgumentNotValidExceptionDetailsBuilder timestamp(LocalDateTime timestamp){
            exceptionDetails.timestamp = timestamp;
            return this;
        }

        public MethodArgumentNotValidExceptionDetails build(){
            return this.exceptionDetails;
        }

    }
}
