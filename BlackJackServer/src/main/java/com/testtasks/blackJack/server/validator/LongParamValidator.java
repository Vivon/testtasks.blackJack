package com.testtasks.blackJack.server.validator;

import org.springframework.stereotype.Component;

@Component
public class LongParamValidator implements ValidatorI<String, Long> {

    public void validate(String paramAlias, Long value) throws ParamValidationException {
        if (value == null || value.equals(0L)) {
            throw new ParamValidationException(paramAlias);
        }
    }
}
