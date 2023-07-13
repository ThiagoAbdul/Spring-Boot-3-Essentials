package com.estudos.springframework.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@Builder
public class ForbbidenExceptionDetails extends ExceptionDetails{

    public ForbbidenExceptionDetails(Exception e) {
        super(e, HttpStatus.FORBIDDEN);
    }
}
