package com.luxoft.webapp.server.request;

import com.luxoft.webapp.server.HttpMethod;
import com.luxoft.webapp.server.exception.ErrorType;
import com.luxoft.webapp.server.exception.ServerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestParser {

    public Request parse(BufferedReader reader) throws IOException {
        Request request = new Request();
        String line = reader.readLine();

        injectUriAndMethod(line, request);
        injectHeaders(reader, request);

        if(request.getMethod() == null){
            throw new ServerException(ErrorType.BAD_REQUEST);
        }if(!request.getMethod().equals(HttpMethod.GET)){
            throw new ServerException(ErrorType.METHOD_NOT_ALLOWED);
        }
        return request;
    }

    public static void injectUriAndMethod(String line, Request request) {
        String uri = null;

        Pattern uriPattern = Pattern.compile("\\s.*[A-Za-z]+\\.[a-z]{2,}\\s");
        Matcher uriMatcher = uriPattern.matcher(line);
        while (uriMatcher.find()) {
            uri = uriMatcher.group().trim();
        }
        request.setUri(uri);

        Pattern methodPattern = Pattern.compile("^[A-Z]*");
        Matcher methodMatcher = methodPattern.matcher(line);

        while (methodMatcher.find()) {

            if (methodMatcher.group().equals(HttpMethod.GET.toString())) {
               request.setMethod(HttpMethod.GET);
            } else if (methodMatcher.group().equals(HttpMethod.POST.toString())) {
                request.setMethod(HttpMethod.POST);
            }else if (methodMatcher.group().equals(HttpMethod.DELETE.toString())) {
                request.setMethod(HttpMethod.DELETE);
            }

        }
    }

    public static void injectHeaders(BufferedReader reader, Request request) throws IOException {
        Map<String, String> headers = new HashMap<>();

        while (true) {
            String head = reader.readLine();
            if (head.equals("")) {
                break;
            }

            String[] splitted = head.split(":\\s");
            headers.put(splitted[0], splitted[1]);
        }

        request.setHeaders(headers);
    }
}
