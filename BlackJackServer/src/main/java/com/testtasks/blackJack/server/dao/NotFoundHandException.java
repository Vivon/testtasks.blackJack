package com.testtasks.blackJack.server.dao;

import com.testtasks.blackJack.server.exception.Error;

public class NotFoundHandException extends DaoException{

    private static final Error ERROR = Error.NOT_FOUND_HAND;

    private Long gameId;

    public NotFoundHandException(Long gameId) {
        super(ERROR);
        this.gameId = gameId;
    }

    @Override
    public String getMessage() {
        return String.format(ERROR.getMessage(), gameId);
    }
}
