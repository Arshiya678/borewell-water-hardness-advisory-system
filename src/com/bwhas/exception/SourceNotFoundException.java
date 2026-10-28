package com.bwhas.exception;

/**
 * Thrown when a requested water source (borewell / open well) does not
 * exist in the database.
 */
public class SourceNotFoundException extends Exception {

    public SourceNotFoundException(String message) {
        super(message);
    }
}