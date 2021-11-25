package com.luxoft.webapp.server.request;

import com.luxoft.webapp.server.exception.ServerException;
import org.junit.jupiter.api.Test;

import javax.lang.model.type.ErrorType;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RequestParserTest {
    Request request = new Request();

    @Test
    public void testDifferentValidRequestMethod() {
        RequestParser.injectUriAndMethod("GET /index.html HTTP/1.1", request);
        assertEquals("GET", request.getMethod().toString());

        RequestParser.injectUriAndMethod("DELETE /index.html HTTP/1.1", request);
        assertEquals("DELETE", request.getMethod().toString());

        RequestParser.injectUriAndMethod("POST /index.html HTTP/1.1", request);
        assertEquals("POST", request.getMethod().toString());
    }

    @Test
    public void testDifferentNonValidRequestMethod() {
        RequestParser.injectUriAndMethod("get /index.html HTTP/1.1", request);
        assertNull(request.getMethod());

        RequestParser.injectUriAndMethod(" get /index.html HTTP/1.1", request);
        assertNull(request.getMethod());

        RequestParser.injectUriAndMethod("gET /index.html HTTP/1.1", request);
        assertNull(request.getMethod());

        RequestParser.injectUriAndMethod("GEt /index.html HTTP/1.1", request);
        assertNull(request.getMethod());

        RequestParser.injectUriAndMethod("GE T /index.html HTTP/1.1", request);
        assertNull(request.getMethod());

        RequestParser.injectUriAndMethod("GTE /index.html HTTP/1.1", request);
        assertNull(request.getMethod());
    }

    @Test
    public void validationUriParsingFromValidRequest() {
        RequestParser.injectUriAndMethod("GET /index.html HTTP/1.1", request);
        assertEquals("/index.html", request.getUri());

        RequestParser.injectUriAndMethod("GET /webapp/css/styles.css HTTP/1.1", request);
        assertEquals("/webapp/css/styles.css", request.getUri());
    }

    @Test
    public void validationIncorrectUriReturnNull() {
        RequestParser.injectUriAndMethod("GET /index.htMl HTTP/1.1", request);
        assertNull(request.getUri());

        RequestParser.injectUriAndMethod("GET /.html HTTP/1.1", request);
        assertNull(request.getUri());

        RequestParser.injectUriAndMethod("GET /a/b/c/d/e/.html HTTP/1.1", request);
        assertNull(request.getUri());

        RequestParser.injectUriAndMethod("GET index. HTTP/1.1", request);
        assertNull(request.getUri());
    }

    @Test
    public void headersValidation() throws IOException {
        String testGetRequest = "Host: localhost:3000\n" +
                "Connection: keep-alive\n" +
                "sec-ch-ua-mobile: ?0\n" +
                "sec-ch-ua-platform: \"Windows\"\n" +
                "\n" +
                "\n";

        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "localhost:3000");
        headers.put("Connection", "keep-alive");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");

        File file = File.createTempFile("test", ".tmp");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(testGetRequest);
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            RequestParser.injectHeaders(bufferedReader, request);
        }
        assertEquals(headers, request.getHeaders());
    }

    @Test
    public void validateBadRequestException() throws IOException {
        String testGetRequest = "GEt /index.html HTTP/1.1\n" +
                "sec-ch-ua-platform: \"Windows\"\n" +
                "\n" +
                "\n";

        File file = File.createTempFile("test1", ".tmp");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(testGetRequest);
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            assertThrows(ServerException.class, () -> {
                new RequestParser().parse(bufferedReader);
            });
        }
    }
}
