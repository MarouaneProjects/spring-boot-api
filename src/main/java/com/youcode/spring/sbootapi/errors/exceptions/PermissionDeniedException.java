package com.youcode.spring.sbootapi.errors.exceptions;

public class PermissionDeniedException extends RuntimeException {

    public PermissionDeniedException(String message) {
        super(message);
    }

    public PermissionDeniedException() {

    }
}
