package com.tomasrodcas.rundeck.plugin.notification.validators.body;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;


/**
 * Validator for checking if the request body is valid XML.
 */
public class XmlBodyValidator implements IBodyValidator {

    private static final Logger logger = LoggerFactory.getLogger(XmlBodyValidator.class);

      /**
     * Validates the request body as XML.
     *
     * @param body The request body to validate
     * @throws InvalidBodyException If the body is not valid XML
     */
    @Override
    public void validate(String body) throws InvalidBodyException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.parse(new ByteArrayInputStream(body.getBytes()));
            logger.info("Valid XML body.");
        } catch (SAXException | ParserConfigurationException | IOException e) {
            String message = "Invalid XML body: " + e.getMessage();
            logger.error(message);
            throw new InvalidBodyException(message, e);
        }
    }
}
