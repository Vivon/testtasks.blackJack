package com.testtasks.blackJack.server.validator;

import com.testtasks.blackJack.server.common.RequestAlias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class HeaderParamsValidator implements ValidatorI<Long, Long> {

    @Autowired
    private LongParamValidator longParamValidator;

    public void validate(Long accountId, Long gameId) throws ParamValidationException {
        longParamValidator.validate(RequestAlias.ACCOUNT_ID_ALIAS, accountId);
        longParamValidator.validate(RequestAlias.GAME_ID_ALIAS, gameId);
    }

    void setLongParamValidator(LongParamValidator longParamValidator) {
        this.longParamValidator = longParamValidator;
    }
}
