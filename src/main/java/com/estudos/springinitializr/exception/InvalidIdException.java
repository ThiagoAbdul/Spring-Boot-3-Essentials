package com.estudos.springinitializr.exception;

public class InvalidIdException extends Exception{

    public InvalidIdException() {
        super("Invalid Id");
    }

    public InvalidIdException(String message) {
        super(message);
    }
}
