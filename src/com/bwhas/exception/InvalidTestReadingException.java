package com.bwhas.exception;

/**
 * Thrown when a water quality test reading is physically invalid
 * (negative values, out-of-range pH, etc.)
 */
public class InvalidTestReadingException extends Exception {

    public InvalidTestReadingException(String message) {
        super(message);
    }

    public InvalidTestReadingException(String message, Throwable cause) {
        super(message, cause);
    }
}