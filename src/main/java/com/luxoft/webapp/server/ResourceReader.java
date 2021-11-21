package com.luxoft.webapp.server;

public class ResourceReader {
    private String webAppPath;

    public String readResources(String uri){
        return this.webAppPath + uri;
    }

    public String getWebAppPath() {
        return webAppPath;
    }

    public void setWebAppPath(String webAppPath) {
        this.webAppPath = webAppPath;
    }
}
