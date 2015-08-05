package com.testtasks.blackJack.server.dao;

import com.testtasks.blackJack.server.exception.*;
import com.testtasks.blackJack.server.exception.Error;

public class DaoException extends ServiceException{

    public DaoException(Error error, Throwable cause) {
        super(error, cause);
    }

    public DaoException(Error error) {
        super(error);
    }
}
