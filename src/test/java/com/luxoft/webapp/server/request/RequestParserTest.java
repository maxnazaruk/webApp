package com.luxoft.webapp.server.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RequestParserTest {

    @Test
    public void testDifferentValidRequestMethod(){
        Request request = new Request();
        RequestParser.injectUriAndMethod("GET /index.html HTTP/1.1", request);
        assertEquals("GET", request.getMethod().toString());

        RequestParser.injectUriAndMethod("DELETE /index.html HTTP/1.1", request);
        assertEquals("DELETE", request.getMethod().toString());

        RequestParser.injectUriAndMethod("POST /index.html HTTP/1.1", request);
        assertEquals("POST", request.getMethod().toString());
    }

    @Test
    public void testDifferentNonValidRequestMethod(){
        Request request = new Request();
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
}
