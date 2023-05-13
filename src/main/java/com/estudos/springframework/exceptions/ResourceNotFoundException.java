package com.estudos.springframework.exceptions;

public class ResourceNotFoundException extends Exception{

    public <T> ResourceNotFoundException(Class<T> clazz) {
        super("Resource not found: " + clazz.getSimpleName());
    }
}
