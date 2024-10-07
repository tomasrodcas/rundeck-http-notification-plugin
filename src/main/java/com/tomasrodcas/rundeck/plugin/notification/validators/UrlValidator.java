package com.tomasrodcas.rundeck.plugin.notification.validators;


import okhttp3.HttpUrl;

/**
 * Service class that validates the URL of an HTTP request.
 */
public class UrlValidator {

    /**
     * Validates the given URL, ensuring it uses the HTTP or HTTPS protocol.
     *
     * @param url The URL to validate
     * @throws IllegalArgumentException If the URL is invalid or uses an unsupported
     *                            protocol
     */
    public void validateUrl(String url) throws IllegalArgumentException {
        if (url == null) {
            throw new IllegalArgumentException("URL cannot be null");
        }

        HttpUrl httpUrl = HttpUrl.parse(url);
        if (httpUrl == null) {
            throw new IllegalArgumentException("Invalid URL format: " + url);
        }

        String scheme = httpUrl.scheme();
        if (!"http".equalsIgnoreCase(scheme) && !"https".equalsIgnoreCase(scheme)) {
            throw new IllegalArgumentException("Only HTTP/HTTPS protocols are supported: " + url);
        }
    }
}
