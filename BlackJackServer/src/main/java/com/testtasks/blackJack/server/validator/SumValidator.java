package com.testtasks.blackJack.server.validator;

import com.testtasks.blackJack.server.validator.ParamValidationException;
import com.testtasks.blackJack.server.validator.ValidatorI;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SumValidator implements ValidatorI<String, BigDecimal> {

    public void validate(String paramAlias, BigDecimal value) throws ParamValidationException {
        if (value == null || value.equals(BigDecimal.ZERO)) {
            throw new ParamValidationException(paramAlias);
        }
    }
}
