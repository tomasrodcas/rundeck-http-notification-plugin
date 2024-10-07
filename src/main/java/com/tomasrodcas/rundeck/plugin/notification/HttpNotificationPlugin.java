package com.tomasrodcas.rundeck.plugin.notification;

import com.dtolabs.rundeck.plugins.descriptions.SelectValues;
import com.dtolabs.rundeck.plugins.notification.NotificationPlugin;
import com.tomasrodcas.rundeck.plugin.notification.validators.RequestValidator;
import com.tomasrodcas.rundeck.plugin.notification.validators.body.InvalidBodyException;
import com.dtolabs.rundeck.core.plugins.Plugin;
import com.dtolabs.rundeck.plugins.descriptions.PluginDescription;
import com.dtolabs.rundeck.plugins.descriptions.PluginProperty;
import okhttp3.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Rundeck plugin that sends HTTP notifications based on job events.
 * This plugin constructs and sends HTTP requests based on user-defined
 * configurations
 * such as URL, method, headers, content type, and body.
 */
@Plugin(service = "Notification", name = "HttpNotificationPlugin")
@PluginDescription(title = "HTTP Notification Plugin", description = "A dynamic plugin that sends HTTP requests based on user input.")
public class HttpNotificationPlugin implements NotificationPlugin {

    @PluginProperty(name = "url", title = "URL", description = "Target URL for the HTTP request", required = true)
    String url;

    @PluginProperty(name = "method", title = "HTTP Method", description = "HTTP method to be used", required = true)
    @SelectValues(values = { "GET", "POST", "PUT", "DELETE", "PATCH" })
    String method;

    @PluginProperty(name = "body", title = "Request Body", description = "Content for POST/PUT methods", required = false)
    String body;

    @PluginProperty(name = "contentType", title = "Content Type", description = "Content Type for the request", required = false)
    @SelectValues(values = { "application/json", "application/xml", "text/plain", "multipart/form-data" })
    String contentType;

    @PluginProperty(name = "headers", title = "Headers", description = "Custom headers as key:value pairs (comma-separated)", required = false)
    String headers;

    private OkHttpClient client = new OkHttpClient();

    private static final Logger logger = LoggerFactory.getLogger(HttpNotificationPlugin.class);

    private RequestValidator requestValidator = new RequestValidator();
    private RequestBuilderService requestBuilderService = new RequestBuilderService();

    /**
     * Sends a notification by constructing and executing an HTTP request.
     *
     * @param trigger       The event that triggered the notification
     * @param executionData Data related to the job execution
     * @param config        Configuration provided by the user
     * @return true if the notification was successfully sent, false otherwise
     */
    @Override
    public boolean postNotification(String trigger, Map executionData, Map config) {
        try {
            requestValidator.validate(this.url, this.method, this.headers, this.body, this.contentType);

            logger.info("Sending HTTP request to: {}", this.url);

            Request request = requestBuilderService.buildRequest(this.url, this.method, this.body, this.contentType,
                    this.headers);

            try (Response response = client.newCall(request).execute()) {
                int responseCode = response.code();

                logger.info("HTTP Response Code: {}", responseCode);
                if (response.isSuccessful()) {
                    logger.info("HTTP Response Body: {}", response.body().string());
                    return true;
                }

                logger.error("Request failed with HTTP code: {}", responseCode);
                return false;

            }
        } catch (InvalidBodyException e) {
            logger.error("Invalid request body: {}", e.getMessage());
        } catch (IOException e) {
            logger.error("Request failed: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("Invalid request: {}", e.getMessage());
        }
        return false;
    }

    /**
     * Sets the OkHttpClient instance to be used for sending HTTP requests. Mainly
     * used for testing purposes.
     *
     * @param client The OkHttpClient instance
     */
    public void setHttpClient(OkHttpClient client) {
        this.client = client;
    }
}
