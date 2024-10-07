package com.tomasrodcas.rundeck.plugin.notification;

import okhttp3.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class RequestBuilderServiceTest {

    private RequestBuilderService requestBuilderService;

    @BeforeEach
    public void setUp() {
        requestBuilderService = new RequestBuilderService();
    }

    @Test
    public void testBuildRequest_GetRequest_Success() {
        String headers = "Authorization: Bearer token";

        Request request = requestBuilderService.buildRequest("https://example.com", "GET", null, null, headers);

        assertEquals("GET", request.method());
        assertEquals("https://example.com/", request.url().toString());
        assertEquals("Bearer token", request.header("Authorization"));
    }

    @Test
    public void testBuildRequest_PostRequest_Success() {
        String headers = "Authorization: Bearer token";

        String body = "{\"key\":\"value\"}";
        Request request = requestBuilderService.buildRequest("https://example.com", "POST", body, "application/json", headers);

        assertEquals("POST", request.method());
        assertEquals("https://example.com/", request.url().toString());
        assertEquals("application/json", request.header("Content-Type"));
        assertNotNull(request.body());
    }

    
}

