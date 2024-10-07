package com.tomasrodcas.rundeck.plugin.notification.validators.body;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validator for checking if the request body is valid JSON using Jackson.
 */
public class JsonBodyValidator implements IBodyValidator {

    private static final Logger logger = LoggerFactory.getLogger(JsonBodyValidator.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Validates the request body as JSON.
     *
     * @param body The request body to validate
     * @throws InvalidBodyException If the body is not valid JSON
     */
    @Override
    public void validate(String body) throws InvalidBodyException {
        try {
            // Attempt to parse the body as JSON using Jackson
            objectMapper.readTree(body);
            logger.info("Valid JSON body.");
        } catch (JsonProcessingException e) {
            String message = "Invalid JSON body: " + e.getMessage();
            logger.error(message);
            throw new InvalidBodyException(message, e);
        }
    }
}
