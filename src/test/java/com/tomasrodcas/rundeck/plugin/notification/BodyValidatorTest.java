package com.tomasrodcas.rundeck.plugin.notification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tomasrodcas.rundeck.plugin.notification.validators.body.BodyValidator;
import com.tomasrodcas.rundeck.plugin.notification.validators.body.InvalidBodyException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BodyValidatorTest {

    private BodyValidator bodyValidator;

    @BeforeEach
    public void setUp() {
        bodyValidator = new BodyValidator();
    }

    @Test
    public void testValidateJsonBody_Success() throws InvalidBodyException {
        String validJson = "{\"key\":\"value\"}";
        bodyValidator.validateBody(validJson, "application/json");
    }

    @Test
    public void testValidateJsonBody_InvalidJson() {
        String invalidJson = "{\"key\":value}";
        assertThrows(InvalidBodyException.class, () -> {
            bodyValidator.validateBody(invalidJson, "application/json");
        });
    }

    @Test
    public void testValidateXmlBody_Success() throws InvalidBodyException {
        String validXml = "<root><key>value</key></root>";
        bodyValidator.validateBody(validXml, "application/xml");
    }

    @Test
    public void testValidateXmlBody_InvalidXml() {
        String invalidXml = "<root><key>value</key>"; // unclosed tag
        assertThrows(InvalidBodyException.class, () -> {
            bodyValidator.validateBody(invalidXml, "application/xml");
        });
    }

    @Test
    public void testValidateTextBody_Success() throws InvalidBodyException {
        String validText = "This is a valid text.";
        bodyValidator.validateBody(validText, "text/plain");
    }

    @Test
    public void testValidateTextBody_EmptyText() {
        String emptyText = "";
        assertThrows(InvalidBodyException.class, () -> {
            bodyValidator.validateBody(emptyText, "text/plain");
        });
    }

    @Test
    public void testValidateMultipartBody_Success() throws InvalidBodyException {
        String validMultipart = "--boundary\r\nContent-Disposition: form-data; name=\"field1\"\r\n\r\nvalue1\r\n--boundary--";
        bodyValidator.validateBody(validMultipart, "multipart/form-data");
    }

    @Test
    public void testValidateBody_UnsupportedContentType() {
        String body = "some body";
        assertThrows(IllegalArgumentException.class, () -> {
            bodyValidator.validateBody(body, "unsupported/type");
        });
    }

    @Test
    public void testValidateBody_EmptyBody() {
        String emptyBody = "";
        assertThrows(InvalidBodyException.class, () -> {
            bodyValidator.validateBody(emptyBody, "application/json");
        });
    }
}
