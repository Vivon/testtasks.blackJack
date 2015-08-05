package com.testtasks.blackJack.server.exception;

public class SystemException extends ServiceException {

    private static final Error ERROR = Error.NOT_KNOWN_EXCEPTION_ERROR;

    public SystemException(Throwable cause) {
        super(ERROR, cause);
    }
}
