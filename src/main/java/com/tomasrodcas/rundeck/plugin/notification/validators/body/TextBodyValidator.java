package com.tomasrodcas.rundeck.plugin.notification.validators.body;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validator for checking if the request body is valid plain text.
 */
public class TextBodyValidator implements IBodyValidator {

    private static final Logger logger = LoggerFactory.getLogger(TextBodyValidator.class);

     /**
     * Validates the request body as plain text.
     *
     * @param body The request body to validate
     * @throws InvalidBodyException If the body is empty or contains only whitespace
     */
    @Override
    public void validate(String body) throws InvalidBodyException {
        if (body.trim().isEmpty()) {
            throw new InvalidBodyException("Text body is empty or contains only whitespace.");
        }
        logger.info("Valid text/plain body.");
    }
}
