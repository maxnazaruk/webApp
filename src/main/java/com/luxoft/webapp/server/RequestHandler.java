package com.luxoft.webapp.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class RequestHandler {
    private BufferedReader socketReader;
    private BufferedWriter socketWriter;
    private String webAppPath;

    public RequestHandler(BufferedReader socketReader, BufferedWriter socketWriter) {
        this.socketReader = socketReader;
        this.socketWriter = socketWriter;
    }

    public void handle() throws IOException {
        RequestParser parser = new RequestParser();
        Request request = parser.parse(this.socketReader);

        ResourceReader resourceReader = new ResourceReader();
        resourceReader.setWebAppPath(webAppPath);

        ResponseWriter writer = new ResponseWriter();
        writer.writeSuccessRepsonse(resourceReader.readResources(request.getUri()), socketWriter);
    }

    public BufferedReader getSocketReader() {
        return socketReader;
    }

    public void setSocketReader(BufferedReader socketReader) {
        this.socketReader = socketReader;
    }

    public BufferedWriter getSocketWriter() {
        return socketWriter;
    }

    public void setSocketWriter(BufferedWriter socketWriter) {
        this.socketWriter = socketWriter;
    }

    public String getWebAppPath() {
        return webAppPath;
    }

    public void setWebAppPath(String webAppPath) {
        this.webAppPath = webAppPath;
    }
}
