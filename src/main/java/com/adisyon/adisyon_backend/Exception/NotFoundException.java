package com.adisyon.adisyon_backend.Exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String value) {
        super("'" + value.toUpperCase() + "' not found");
    }

}
