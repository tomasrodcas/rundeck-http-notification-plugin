package com.tomasrodcas.rundeck.plugin.notification.validators;

/**
 * Service class that validates the headers of an HTTP request.
 */
public class HttpHeaderValidator {

    /**
     * Validates the headers, ensuring each is in the key:value format.
     *
     * @param headers The headers to validate, formatted as a comma-separated list of key:value pairs
     * @throws IllegalArgumentException If any header is not properly formatted
     */
    public void validateHeaders(String headers) {
        if (headers != null && !headers.isEmpty()) {
            String[] pairs = headers.split(",");
            for (String pair : pairs) {
                String[] keyValue = pair.split(":");
                if (keyValue.length != 2) {
                    throw new IllegalArgumentException("Invalid header format: " + pair);
                }
            }
        }
    }
}