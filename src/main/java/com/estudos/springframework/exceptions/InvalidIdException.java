package com.estudos.springframework.exceptions;

public class InvalidIdException extends Exception{

    public InvalidIdException() {
        super("Invalid Id");
    }

    public InvalidIdException(String message) {
        super(message);
    }
}
