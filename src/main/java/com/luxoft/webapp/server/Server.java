package com.luxoft.webapp.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    private int port;
    private String webAppPath;

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.setPort(3000);
        server.setWebAppPath("src/main/resources/webapp");
        server.start();
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port);
             Socket socket = serverSocket.accept();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            RequestHandler requestHandler = new RequestHandler(bufferedReader, bufferedWriter);
            requestHandler.setWebAppPath(webAppPath);
            requestHandler.handle();
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getWebAppPath() {
        return webAppPath;
    }

    public void setWebAppPath(String webAppPath) {
        this.webAppPath = webAppPath;
    }
}
