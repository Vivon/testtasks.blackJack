package com.testtasks.blackJack.server.exception;

public class NotEnoughMoneyException extends ServiceException {

    private static final Error ERROR = Error.NOT_ENOUGH_MONEY;

    public NotEnoughMoneyException() {
        super(ERROR);
    }
}
