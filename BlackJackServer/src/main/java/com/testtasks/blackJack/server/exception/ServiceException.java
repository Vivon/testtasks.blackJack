package com.testtasks.blackJack.server.exception;

public class ServiceException extends Exception {

    private Error error;

    public ServiceException(Error error) {
        super(error.getMessage());
        this.error = error;
    }

    public ServiceException(Error error, Throwable cause) {
        super(error.getMessage(), cause);
        this.error = error;
    }

    public Error getError() {
        return error;
    }
}
