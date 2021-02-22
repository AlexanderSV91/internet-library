package com.faceit.example.exception;

public class ResourceNotFoundException extends RuntimeException {

    public static final String NOT_FOUND = "Not found";

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
