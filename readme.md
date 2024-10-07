# HTTP Notification Plugin for Rundeck

### Overview

This project is a dynamic plugin for Rundeck that allows sending HTTP notifications based on job events. The plugin uses OkHttp to send HTTP requests and supports multiple HTTP methods (GET, POST, PUT, DELETE, PATCH) with custom headers and request bodies.
Features:

    Supports GET, POST, PUT, DELETE, PATCH HTTP methods.

    Customizable headers and request body for POST/PUT/PATCH requests.

    JSON, XML, text/plain, and multipart/form-data validation for request bodies.

    Validation for URL formats, HTTP methods, headers, and request bodies.

Requirements

    JDK 11
    Maven 3.x
    Rundeck (Tested on version X.X)

Dependencies

    OkHttp: For making HTTP requests.
    Jackson: For JSON body validation.
    JUnit 5: For unit testing.
    Mockito: For mocking during unit tests.


Installation

Clone the repository:

    git clone <repo-url>
    cd <repo-directory>

Build the project:

Use Maven to build the plugin.

    mvn clean package

This will create a JAR file for the plugin in the target/ directory.

Install the Plugin in Rundeck:

Copy the generated JAR file into your Rundeck libext directory:



    cp target/http-notification-plugin.jar /path/to/rundeck/libext/

Restart Rundeck to load the plugin.

Usage

Once installed, the plugin will be available in the Rundeck GUI for configuring job notifications.
Configuration Properties:

    URL: The target URL for the HTTP request (required).

    HTTP Method: The HTTP method to use (GET, POST, PUT, DELETE, PATCH).

    Request Body: The content of the request body (for POST, PUT, PATCH methods).
    
    Content Type: The content type of the request body (e.g., application/json, application/xml, etc.).

    Headers: Custom headers as key:value pairs, comma-separated (e.g., Authorization: Bearer token,Content-Type: application/json).


Example:

    URL: https://example.com/api/notify
    HTTP Method: POST
    Request Body:

    json

    {
        "message": "Job completed successfully",
        "job": "JobName"
    }

    Content Type: application/json
    Headers: Authorization: Bearer your-token

Testing

The project includes unit tests for all the plugin's functionality, including validation and HTTP requests. Tests are written using JUnit 5 and Mockito for mocking dependencies.

To run the tests:

    mvn test

The test suite covers:

    Validation of request URLs, HTTP methods, headers, and bodies.
    Testing for successful and failed HTTP requests.
    Mocking of HTTP responses for different scenarios (e.g., 200 OK, 500 Internal Server Error).

