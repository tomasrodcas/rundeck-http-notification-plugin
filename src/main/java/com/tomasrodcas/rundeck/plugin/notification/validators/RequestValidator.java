package com.tomasrodcas.rundeck.plugin.notification.validators;

import java.net.URISyntaxException;

import com.tomasrodcas.rundeck.plugin.notification.validators.body.BodyValidator;
import com.tomasrodcas.rundeck.plugin.notification.validators.body.InvalidBodyException;

/**
 * Service class that validates an HTTP request.
 * It validates the URL, method, headers, and body.
 */
public class RequestValidator {

    private final UrlValidator urlValidator = new UrlValidator();
    private final HttpMethodValidator methodValidator = new HttpMethodValidator();
    private final HttpHeaderValidator headerValidator = new HttpHeaderValidator();
    private final BodyValidator bodyValidator = new BodyValidator();

    /**
     * Validates the entire HTTP request.
     *
     * @param url         The request URL to validate
     * @param method      The HTTP method to validate (GET, POST, PUT, DELETE, PATCH)
     * @param headers     The headers in key:value format
     * @param body        The body of the request
     * @param contentType The content type of the request body (application/json, etc.)
     * @throws IllegalArgumentException If any part of the request is invalid, except for the body
     * @throws InvalidBodyException If the body is invalid based on the content type
     */
    public void validate(String url, String method, String headers, String body, String contentType)
            throws IllegalArgumentException, InvalidBodyException {
        urlValidator.validateUrl(url);
        methodValidator.validateMethod(method);
        headerValidator.validateHeaders(headers);

        if (methodRequiresBody(method)) {
            bodyValidator.validateBody(body, contentType);
        }
    }

    private boolean methodRequiresBody(String method) {
        return method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT") || method.equalsIgnoreCase("PATCH");
    }
}
