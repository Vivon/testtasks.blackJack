package com.testtasks.blackJack.server.validator;

import com.testtasks.blackJack.server.common.RequestAlias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SumRequestValidator implements ValidatorI<Long, BigDecimal> {

    @Autowired
    private SumValidator sumValidator;

    @Autowired
    private LongParamValidator longParamValidator;

    public void validate(Long accountId, BigDecimal sum) throws ParamValidationException {
        longParamValidator.validate(RequestAlias.ACCOUNT_ID_ALIAS, accountId);
        sumValidator.validate(RequestAlias.SUM_TO_REPLENISH_ALIAS, sum);
    }

    void setSumValidator(SumValidator sumValidator) {
        this.sumValidator = sumValidator;
    }

    void setLongParamValidator(LongParamValidator longParamValidator) {
        this.longParamValidator = longParamValidator;
    }
}
