package com.luxoft.webapp.server.request;

import com.luxoft.webapp.server.exception.ServerException;
import com.luxoft.webapp.server.resourcereader.ResourceReader;
import com.luxoft.webapp.server.writer.ResponseWriter;

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
        ResponseWriter writer = new ResponseWriter();
        Request request = null;
        try {
            request = parser.parse(this.socketReader);

            ResourceReader resourceReader = new ResourceReader();
            resourceReader.setWebAppPath(webAppPath);

            writer.writeSuccessResponse(resourceReader.readResources(request.getUri()), socketWriter);
        } catch (ServerException exception) {
            writer.writeErrorResponse(socketWriter, exception);
        }
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
