package com.tomasrodcas.rundeck.plugin.notification.validators.body;

import java.util.HashMap;
import java.util.Map;

/**
 * Service class responsible for validating the request body based on the content type.
 * This class uses a map to delegate body validation to specific content type validators
 * such as JSON, XML, plain text, and multipart/form-data.
 */
public class BodyValidator {

    private Map<String, IBodyValidator> contentTypeValidators = new HashMap<>();

    /**
     * Initializes the BodyValidator with the supported content type validators.
     * The supported content types are application/json, application/xml, text/plain, and multipart/form-data.
     */
    public BodyValidator() {
        contentTypeValidators.put("application/json", new JsonBodyValidator());
        contentTypeValidators.put("application/xml", new XmlBodyValidator());
        contentTypeValidators.put("text/plain", new TextBodyValidator());
        contentTypeValidators.put("multipart/form-data", new MultipartBodyValidator());
    }

    /**
     * Validates the request body based on the specified content type.
     *
     * @param body        The request body to validate
     * @param contentType The content type of the request body (e.g., application/json, application/xml)
     * @throws InvalidBodyException If the body is null, empty, or invalid for the given content type
     * @throws IllegalArgumentException If the content type is unsupported
     */
    public void validateBody(String body, String contentType) throws InvalidBodyException {
        if (body == null || body.isEmpty()) {
            throw new InvalidBodyException("Request body is required for POST/PUT methods");
        }

        IBodyValidator validator = contentTypeValidators.get(contentType.toLowerCase());

        if (validator != null) {
            validator.validate(body);
        } else {
            throw new IllegalArgumentException("Unsupported content type: " + contentType);
        }
    }
}
