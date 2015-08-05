package com.testtasks.blackJack.server.validator;

import com.testtasks.blackJack.server.common.RequestAlias;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class HeaderParamsValidatorTest {

    private LongParamValidator longParamValidatorMock;

    private HeaderParamsValidator validatorForTests = new HeaderParamsValidator();

    private static final Long DEFAULT_GAME_ID = 12345L;
    private static final Long DEFAULT_ACCOUNT_ID = 987654L;

    @Before
    public void initData(){
        longParamValidatorMock = mock(LongParamValidator.class);

        validatorForTests.setLongParamValidator(longParamValidatorMock);
    }

    @Test
    public void testSuccessValidate() throws ParamValidationException {
        validatorForTests.validate(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);

        verify(longParamValidatorMock).validate(RequestAlias.ACCOUNT_ID_ALIAS, DEFAULT_ACCOUNT_ID);
        verify(longParamValidatorMock).validate(RequestAlias.GAME_ID_ALIAS, DEFAULT_GAME_ID);
    }

    @Test
    public void testLongParamThrowExceptionOnAccountId() throws ParamValidationException {
        ParamValidationException exForTest = new ParamValidationException(RequestAlias.ACCOUNT_ID_ALIAS);
        doThrow(exForTest).when(longParamValidatorMock).validate(RequestAlias.ACCOUNT_ID_ALIAS, DEFAULT_ACCOUNT_ID);
        try {
            validatorForTests.validate(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            fail();
        } catch (ParamValidationException ex){
            assertEquals(exForTest, ex);
            verify(longParamValidatorMock).validate(RequestAlias.ACCOUNT_ID_ALIAS, DEFAULT_ACCOUNT_ID);
            verify(longParamValidatorMock, never()).validate(RequestAlias.GAME_ID_ALIAS, DEFAULT_GAME_ID);
        }
    }

    @Test
    public void testLongParamThrowExceptionOnGameId() throws ParamValidationException {
        ParamValidationException exForTest = new ParamValidationException(RequestAlias.GAME_ID_ALIAS);
        doThrow(exForTest).when(longParamValidatorMock).validate(RequestAlias.GAME_ID_ALIAS, DEFAULT_GAME_ID);
        try {
            validatorForTests.validate(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            fail();
        } catch (ParamValidationException ex){
            assertEquals(exForTest, ex);
            verify(longParamValidatorMock).validate(RequestAlias.ACCOUNT_ID_ALIAS, DEFAULT_ACCOUNT_ID);
            verify(longParamValidatorMock).validate(RequestAlias.GAME_ID_ALIAS, DEFAULT_GAME_ID);
        }
    }
}
