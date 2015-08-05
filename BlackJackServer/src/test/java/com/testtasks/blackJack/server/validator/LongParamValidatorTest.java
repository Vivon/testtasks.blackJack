package com.testtasks.blackJack.server.validator;

import com.testtasks.blackJack.server.common.RequestAlias;
import com.testtasks.blackJack.server.exception.Error;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class LongParamValidatorTest {

    private LongParamValidator validatorForTests = new LongParamValidator();

    private static final Long DEFAULT_VALUE = 10L;

    @Test
    public void testSuccessRequest() throws ParamValidationException {
        validatorForTests.validate(RequestAlias.GAME_ID_ALIAS, DEFAULT_VALUE);
    }

    @Test
    public void testWhenValueIsNull() {
        try {
            validatorForTests.validate(RequestAlias.GAME_ID_ALIAS, null);
            fail();
        } catch (ParamValidationException e) {
            assertEquals(com.testtasks.blackJack.server.exception.Error.WRONG_PARAM, e.getError());
            String expectedMessage = String.format(Error.WRONG_PARAM.getMessage(), RequestAlias.GAME_ID_ALIAS);
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void testWhenValueIsZero() {
        try {
            validatorForTests.validate(RequestAlias.GAME_ID_ALIAS, 0L);
            fail();
        } catch (ParamValidationException e) {
            assertEquals(Error.WRONG_PARAM, e.getError());
            String expectedMessage = String.format(Error.WRONG_PARAM.getMessage(), RequestAlias.GAME_ID_ALIAS);
            assertEquals(expectedMessage, e.getMessage());
        }
    }
}
