package com.example.project.exception;

public class AppBadRequestException extends RuntimeException {
    public AppBadRequestException(String message) {
        super(message);
    }
}
