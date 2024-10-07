package com.tomasrodcas.rundeck.plugin.notification;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Service class responsible for building HTTP requests.
 * This class handles creating the HTTP request based on the URL, method, body, content type, and headers.
 */
public class RequestBuilderService {

     /**
     * Builds an HTTP request based on the provided URL, method, body, content type, and headers.
     *
     * @param url         The URL to send the request to
     * @param method      The HTTP method (GET, POST, PUT, DELETE, PATCH)
     * @param body        The request body (if applicable)
     * @param contentType The content type of the request body (e.g., application/json)
     * @param headers     A map of headers to include in the request
     * @return The constructed HTTP request
     */
    public Request buildRequest(String url, String method, String body, String contentType, String headers) {

        RequestBody requestBody = null;

        if (methodRequiresBody(method)) {
            requestBody = RequestBody.create(MediaType.parse(contentType), body);
        }

        Request.Builder builder = new Request.Builder().url(url);

        if ( contentType != null && !contentType.isEmpty() ) {
            builder.addHeader("Content-Type", contentType);
        }

        for (Map.Entry<String, String> header : parseHeaders(headers).entrySet()) {
            builder.addHeader(header.getKey(), header.getValue());
        }

        switch (method.toUpperCase()) {
            case "POST":
                builder.post(requestBody);
                break;
            case "PUT":
                builder.put(requestBody);
                break;
            case "DELETE":
                builder.delete();
                break;
            case "PATCH":
                builder.patch(requestBody);
                break;
            default:
                builder.get();
                break;
        }

        return builder.build();
    }

    /**
     * Parses headers from a string in the format "key:value" and converts them into a map.
     *
     * @param headers The string of headers in key:value format, separated by commas
     * @return A map where the key is the header name and the value is the header value
     */
    public Map<String, String> parseHeaders(String headers) {
        Map<String, String> headersMap = new HashMap<>();

        if (headers != null && !headers.isEmpty()) {
            String[] pairs = headers.split(",");
            for (String pair : pairs) {
                String[] keyValue = pair.split(":");
                if (keyValue.length == 2) {
                    headersMap.put(keyValue[0].trim(), keyValue[1].trim());
                }
            }
        }

        return headersMap;
    }

     /**
     * Checks if the given HTTP method requires a request body.
     *
     * @param method The HTTP method (GET, POST, PUT, DELETE, PATCH)
     * @return true if the method requires a body (POST, PUT, PATCH), false otherwise
     */
    private boolean methodRequiresBody(String method) {
        return method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT") || method.equalsIgnoreCase("PATCH");
    }
}
