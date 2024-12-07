package com.example.demo.exceptions;

public class BadDataException extends RuntimeException {
    public BadDataException(String message) {
        super(message);
    }
}
