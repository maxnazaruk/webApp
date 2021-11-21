package com.luxoft.webapp.server;

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

        String uri = null;

        Pattern uriPattern = Pattern.compile("\\s.*\\s");
        Matcher uriMatcher = uriPattern.matcher(line);
        while (uriMatcher.find()){
            uri = uriMatcher.group().trim();
        }

        request.setUri(uri);

        Map<String, String> headers = new HashMap<>();
        while (true){
            String head = reader.readLine();

            if(head.equals("")){
                break;
            }

            String[] splitted = head.split(":\\s");
            headers.put(splitted[0], splitted[1]);

        }

        request.setHeaders(headers);

        Pattern methodPattern = Pattern.compile("^[A-Z]*");
        Matcher methodMatcher = methodPattern.matcher(line);
        while (methodMatcher.find()){
            if(methodMatcher.group().equals(HttpMethod.GET)){
                request.setMethod(HttpMethod.GET);
            }else if(methodMatcher.group().equals(HttpMethod.POST)){
                request.setMethod(HttpMethod.POST);
            }
        }
        return request;
    }

    private void injectUriAndMethod(String line, Request request){

    }

    private void injectHeaders(BufferedReader reader, Request request){

    }
}
