package com.tomasrodcas.rundeck.plugin.notification.validators.body;

/**
 * Custom exception that is thrown when the request body validation fails.
 */
public class InvalidBodyException extends Exception {

      /**
     * Constructs a new InvalidBodyException with the specified detail message.
     *
     * @param message The detail message for the exception
     */
    public InvalidBodyException(String message) {
        super(message);
    }

    /**
     * Constructs a new InvalidBodyException with the specified detail message and cause.
     *
     * @param message The detail message for the exception
     * @param cause   The cause of the exception
     */
    public InvalidBodyException(String message, Throwable cause) {
        super(message, cause);
    }
}
