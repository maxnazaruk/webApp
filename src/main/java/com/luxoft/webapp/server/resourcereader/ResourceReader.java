package com.luxoft.webapp.server.resourcereader;

import com.luxoft.webapp.server.exception.ErrorType;
import com.luxoft.webapp.server.exception.ServerException;

import java.io.*;
import java.nio.charset.StandardCharsets;


public class ResourceReader {
    private String webAppPath;

    public InputStream readResources(String uri) throws IOException {
        File file = new File(this.webAppPath, uri);

        if(!file.exists()){
            throw new ServerException(ErrorType.NOT_FOUND);
        }

        return new FileInputStream(file);
    }

    public String getWebAppPath() {
        return webAppPath;
    }

    public void setWebAppPath(String webAppPath) {
        this.webAppPath = webAppPath;
    }
}
