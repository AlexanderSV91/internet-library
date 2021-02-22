package com.faceit.example.exception;

public class ResourceAlreadyExists extends RuntimeException {

    private final String nameResource;

    public ResourceAlreadyExists(String message, String nameResource) {
        super(message);
        this.nameResource = nameResource;
    }

    public String getNameResource() {
        return nameResource;
    }
}
