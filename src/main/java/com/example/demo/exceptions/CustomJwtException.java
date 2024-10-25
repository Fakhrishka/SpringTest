package com.example.demo.exceptions;


public class CustomJwtException extends RuntimeException {
    public CustomJwtException(String message) {
        super(message);
    }
}