package com.luxoft.webapp.server.exception;

public class ServerException extends RuntimeException {
    private ErrorType type;

    public ServerException(ErrorType type) {
        this.type = type;
    }

    public ErrorType getType() {
        return type;
    }

    public void setType(ErrorType type) {
        this.type = type;
    }
}
