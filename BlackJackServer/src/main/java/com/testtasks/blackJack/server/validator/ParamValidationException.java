package com.testtasks.blackJack.server.validator;

import com.testtasks.blackJack.server.exception.Error;
import com.testtasks.blackJack.server.exception.ServiceException;

public class ParamValidationException extends ServiceException {

    private static final Error ERROR = Error.WRONG_PARAM;

    private String paramAlias;

    public ParamValidationException(String paramAlias) {
        super(ERROR);
        this.paramAlias = paramAlias;
    }

    @Override
    public String getMessage() {
        return String.format(ERROR.getMessage(), paramAlias);
    }
}
