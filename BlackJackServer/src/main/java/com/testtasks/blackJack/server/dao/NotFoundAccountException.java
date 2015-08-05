package com.testtasks.blackJack.server.dao;

import com.testtasks.blackJack.server.exception.Error;

public class NotFoundAccountException extends DaoException{

    private static final Error ERROR = Error.NOT_FOUND_ACCOUNT;

    private Long accountId;

    public NotFoundAccountException(Long accountId, Throwable cause) {
        super(ERROR, cause);
        this.accountId = accountId;
    }

    @Override
    public String getMessage() {
        return String.format(ERROR.getMessage(), accountId);
    }
}
