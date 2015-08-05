package com.testtasks.blackJack.server.exception;

public class NotValidGameStateException  extends ServiceException {

    private static final Error ERROR = Error.NOT_VALID_GAME_STATE;

    public NotValidGameStateException() {
        super(ERROR);
    }
}
