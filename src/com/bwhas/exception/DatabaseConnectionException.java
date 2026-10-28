package com.bwhas.exception;

/**
 * Unchecked exception thrown when the application cannot establish
 * or maintain a connection to the database.
 */
public class DatabaseConnectionException extends RuntimeException {

    public DatabaseConnectionException(String message) {
        super(message);
    }

    public DatabaseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}