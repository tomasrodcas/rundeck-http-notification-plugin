package com.tomasrodcas.rundeck.plugin.notification;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

public class HttpNotificationPluginTest {

    private HttpNotificationPlugin plugin;
    private OkHttpClient mockClient;
    private Call mockCall;
    private Response mockResponse;
    private ResponseBody mockResponseBody;

    @BeforeEach
    public void setUp() throws IOException {
        plugin = new HttpNotificationPlugin();

        // Inject properties manually (simulating Rundeck)
        plugin.url = "https://example.com";
        plugin.method = "POST";
        plugin.body = "{\"key\":\"value\"}";
        plugin.contentType = "application/json";
        plugin.headers = "Authorization: Bearer token";

        mockClient = mock(OkHttpClient.class);
        mockCall = mock(Call.class);
        mockResponse = mock(Response.class);
        mockResponseBody = mock(ResponseBody.class);

        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);

        when(mockResponse.body()).thenReturn(mockResponseBody);
        when(mockResponseBody.string()).thenReturn("OK");

        plugin.setHttpClient(mockClient);
    }

    @Test
    public void testPostNotification_Success() throws IOException, URISyntaxException {
        when(mockResponse.isSuccessful()).thenReturn(true);
        when(mockResponse.code()).thenReturn(200);

        boolean result = plugin.postNotification("trigger", new HashMap<>(), new HashMap<>());
        assertTrue(result);
    }

    @Test
    public void testPostNotification_Fail() throws IOException {
        when(mockResponse.isSuccessful()).thenReturn(false);
        when(mockResponse.code()).thenReturn(500);

        boolean result = plugin.postNotification("trigger", new HashMap<>(), new HashMap<>());
        assertFalse(result);
    }

    @Test
    public void testInvalidUrl_ThrowsException() throws IOException {
        plugin.url = "htp://invalid-url";

        boolean result = plugin.postNotification("trigger", new HashMap<>(), new HashMap<>());
        assertFalse(result);
    }

    @Test
    public void testInvalidJsonBody_ThrowsException() throws IOException {
        plugin.body = "asd";

        boolean result = plugin.postNotification("trigger", new HashMap<>(), new HashMap<>());
        assertFalse(result);
    }

    @Test
    public void testGetRequest() throws IOException {
        plugin.method = "GET";
        plugin.body = null;  // GET requests do not have a body
        plugin.contentType = null;

        when(mockResponse.isSuccessful()).thenReturn(true);
        when(mockResponse.code()).thenReturn(200);

        boolean result = plugin.postNotification("trigger", new HashMap<>(), new HashMap<>());
        assertTrue(result);
    }

    @Test
    public void testPutRequest() throws IOException {
        plugin.method = "PUT";
        plugin.body = "{\"key\":\"value\"}";
        plugin.contentType = "application/json";

        when(mockResponse.isSuccessful()).thenReturn(true);
        when(mockResponse.code()).thenReturn(200);

        boolean result = plugin.postNotification("trigger", new HashMap<>(), new HashMap<>());
        assertTrue(result);
    }

    @Test
    public void testDeleteRequest() throws IOException {
        plugin.method = "DELETE";
        plugin.body = null;  // DELETE requests typically do not have a body
        plugin.contentType = null;

        when(mockResponse.isSuccessful()).thenReturn(true);
        when(mockResponse.code()).thenReturn(200);

        boolean result = plugin.postNotification("trigger", new HashMap<>(), new HashMap<>());
        assertTrue(result);
    }

    @Test
    public void testPatchRequest() throws IOException {
        plugin.method = "PATCH";
        plugin.body = "{\"key\":\"updatedValue\"}";
        plugin.contentType = "application/json";

        when(mockResponse.isSuccessful()).thenReturn(true);
        when(mockResponse.code()).thenReturn(200);

        boolean result = plugin.postNotification("trigger", new HashMap<>(), new HashMap<>());
        assertTrue(result);
    }

    @Test
    public void testInvalidXmlBody_ThrowsException() throws IOException {
        // Simulate an invalid XML body
        plugin.body = "<key>value</key";  // Missing closing tag

        boolean result = plugin.postNotification("trigger", new HashMap<>(), new HashMap<>());
        assertFalse(result); // The validation should fail due to the invalid XML body
    }

    @Test
    public void testValidTextBody() throws IOException {
        plugin.method = "POST";
        plugin.body = "This is a valid text body";
        plugin.contentType = "text/plain";

        when(mockResponse.isSuccessful()).thenReturn(true);
        when(mockResponse.code()).thenReturn(200);

        boolean result = plugin.postNotification("trigger", new HashMap<>(), new HashMap<>());
        assertTrue(result);
    }

    @Test
    public void testInvalidTextBody_ThrowsException() throws IOException {
        plugin.method = "POST";
        plugin.body = "";  // Invalid empty text body
        plugin.contentType = "text/plain";

        boolean result = plugin.postNotification("trigger", new HashMap<>(), new HashMap<>());
        assertFalse(result);  // The validation should fail due to the empty body
    }

    @Test
    public void testMultipartRequest_Success() throws IOException {
        plugin.method = "POST";
        plugin.body = "--boundary\r\nContent-Disposition: form-data; name=\"field1\"\r\n\r\nvalue1\r\n--boundary--";
        plugin.contentType = "multipart/form-data";

        when(mockResponse.isSuccessful()).thenReturn(true);
        when(mockResponse.code()).thenReturn(200);

        boolean result = plugin.postNotification("trigger", new HashMap<>(), new HashMap<>());
        assertTrue(result);
    }
}
