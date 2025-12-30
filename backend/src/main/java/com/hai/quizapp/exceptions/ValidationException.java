package com.hai.quizapp.exceptions;

/**
 * Exception thrown when a validation error occurs in business logic.
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
