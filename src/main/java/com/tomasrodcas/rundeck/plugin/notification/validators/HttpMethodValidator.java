package com.tomasrodcas.rundeck.plugin.notification.validators;

import java.util.Arrays;
import java.util.List;

/**
 * Service class that validates the HTTP method of a request.
 */
public class HttpMethodValidator {

    private static final List<String> VALID_METHODS = Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH");

     /**
     * Validates the HTTP method.
     *
     * @param method The HTTP method to validate (GET, POST, PUT, DELETE, PATCH)
     * @throws IllegalArgumentException If the HTTP method is invalid
     */
    public void validateMethod(String method) {
        if (!VALID_METHODS.contains(method.toUpperCase())) {
            throw new IllegalArgumentException("Invalid HTTP method: " + method);
        }
    }
}