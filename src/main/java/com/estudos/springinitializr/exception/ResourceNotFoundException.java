package com.estudos.springinitializr.exception;

public class ResourceNotFoundException extends Exception{

    public <T> ResourceNotFoundException(Class<T> clazz) {
        super("Resource not found: " + clazz.getSimpleName());
    }
}
