package com.tomasrodcas.rundeck.plugin.notification.validators.body;

/**
 * Functional interface for validating the body of an HTTP request.
 */
@FunctionalInterface
public interface IBodyValidator {

    /**
     * Validates the request body based on the content type.
     *
     * @param body The request body to validate
     * @throws InvalidBodyException If the body is invalid for the given content type
     */
    void validate(String body) throws InvalidBodyException;
}
