package com.testtasks.blackJack.server.validator;

import com.testtasks.blackJack.server.common.RequestAlias;
import com.testtasks.blackJack.server.exception.Error;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SumValidatorTest {

    private SumValidator validatorForTests = new SumValidator();

    private static final BigDecimal DEFAULT_SUM = BigDecimal.TEN;

    @Test
    public void testSuccessRequest() throws ParamValidationException {
        validatorForTests.validate(RequestAlias.SUM_TO_REPLENISH_ALIAS, DEFAULT_SUM);
    }

    @Test
    public void testWhenValueIsNull() {
        try {
            validatorForTests.validate(RequestAlias.SUM_TO_REPLENISH_ALIAS, null);
            fail();
        } catch (ParamValidationException e) {
            assertEquals(Error.WRONG_PARAM, e.getError());
            String expectedMessage = String.format(Error.WRONG_PARAM.getMessage(), RequestAlias.SUM_TO_REPLENISH_ALIAS);
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void testWhenValueIsZero() {
        try {
            validatorForTests.validate(RequestAlias.SUM_TO_REPLENISH_ALIAS, BigDecimal.ZERO);
            fail();
        } catch (ParamValidationException e) {
            assertEquals(Error.WRONG_PARAM, e.getError());
            String expectedMessage = String.format(Error.WRONG_PARAM.getMessage(), RequestAlias.SUM_TO_REPLENISH_ALIAS);
            assertEquals(expectedMessage, e.getMessage());
        }
    }
}
