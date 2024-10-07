package com.tomasrodcas.rundeck.plugin.notification.validators.body;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validator for checking if the request body is valid multipart/form-data.
 */
public class MultipartBodyValidator implements IBodyValidator {

    private static final Logger logger = LoggerFactory.getLogger(MultipartBodyValidator.class);

    /**
     * Validates the request body as multipart/form-data.
     *
     * @param body The request body to validate
     * @throws InvalidBodyException If the body is empty
     */
    @Override
    public void validate(String body) {
        if (body.isEmpty()) {
            throw new IllegalArgumentException("Multipart body is empty.");
        }
        logger.info("Valid multipart/form-data body.");
    }
}
