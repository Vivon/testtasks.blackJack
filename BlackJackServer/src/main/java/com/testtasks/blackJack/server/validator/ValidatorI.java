package com.testtasks.blackJack.server.validator;

public interface ValidatorI<T, S> {

    void validate(T arg1, S arg2) throws ParamValidationException;
}
