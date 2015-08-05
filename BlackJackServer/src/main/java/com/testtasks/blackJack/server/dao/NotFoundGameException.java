package com.testtasks.blackJack.server.dao;

import com.testtasks.blackJack.server.exception.Error;

public class NotFoundGameException extends DaoException{

    private static final Error ERROR = Error.NOT_FOUND_GAME;

    private Long gameId;
    private Long accountId;

    public NotFoundGameException(Long gameId, Long accountId, Throwable cause) {
        super(ERROR, cause);
        this.gameId = gameId;
        this.accountId = accountId;
    }

    @Override
    public String getMessage() {
        return String.format(ERROR.getMessage(), gameId, accountId);
    }
}
