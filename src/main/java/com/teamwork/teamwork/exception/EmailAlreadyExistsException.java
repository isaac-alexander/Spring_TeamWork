package com.teamwork.teamwork.exception; // create this package if not exists

// Custom exception for duplicate email
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}