package com.testtasks.blackJack.server.dao;

import com.testtasks.blackJack.server.exception.Error;

public class SystemDaoException extends DaoException{

    private static final Error ERROR = Error.DB_PROBLEMS;

    public SystemDaoException(Throwable cause) {
        super(ERROR, cause);
    }

    public SystemDaoException() {
        super(ERROR);
    }
}
