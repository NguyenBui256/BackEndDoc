package com.btvn.resume.model;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}