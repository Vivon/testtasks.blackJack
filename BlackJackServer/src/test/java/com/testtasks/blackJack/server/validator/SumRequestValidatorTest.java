package com.testtasks.blackJack.server.validator;

import com.testtasks.blackJack.server.common.RequestAlias;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class SumRequestValidatorTest {

    private SumValidator sumValidatorMock;
    private LongParamValidator longParamValidatorMock;

    private SumRequestValidator validatorForTests = new SumRequestValidator();

    private static final Long DEFAULT_GAME_ID = 12345L;
    private static final BigDecimal DEFAULT_SUM = BigDecimal.TEN;

    @Before
    public void initData(){
        sumValidatorMock = mock(SumValidator.class);
        longParamValidatorMock = mock(LongParamValidator.class);

        validatorForTests.setLongParamValidator(longParamValidatorMock);
        validatorForTests.setSumValidator(sumValidatorMock);
    }

    @Test
    public void testSuccessValidate() throws ParamValidationException {
        validatorForTests.validate(DEFAULT_GAME_ID, DEFAULT_SUM);

        verify(longParamValidatorMock).validate(RequestAlias.ACCOUNT_ID_ALIAS, DEFAULT_GAME_ID);
        verify(sumValidatorMock).validate(RequestAlias.SUM_TO_REPLENISH_ALIAS, DEFAULT_SUM);
    }

    @Test
    public void testLongParamThrowException() throws ParamValidationException {
        ParamValidationException exForTest = new ParamValidationException(RequestAlias.ACCOUNT_ID_ALIAS);
        doThrow(exForTest).when(longParamValidatorMock).validate(RequestAlias.ACCOUNT_ID_ALIAS, DEFAULT_GAME_ID);
        try {
            validatorForTests.validate(DEFAULT_GAME_ID, DEFAULT_SUM);
            fail();
        } catch (ParamValidationException ex){
            assertEquals(exForTest, ex);
            verify(longParamValidatorMock).validate(RequestAlias.ACCOUNT_ID_ALIAS, DEFAULT_GAME_ID);
            verify(sumValidatorMock, never()).validate(RequestAlias.SUM_TO_REPLENISH_ALIAS, DEFAULT_SUM);
        }
    }

    @Test
    public void testSumParamThrowException() throws ParamValidationException {
        ParamValidationException exForTest = new ParamValidationException(RequestAlias.SUM_TO_REPLENISH_ALIAS);
        doThrow(exForTest).when(sumValidatorMock).validate(RequestAlias.SUM_TO_REPLENISH_ALIAS, DEFAULT_SUM);
        try {
            validatorForTests.validate(DEFAULT_GAME_ID, DEFAULT_SUM);
            fail();
        } catch (ParamValidationException ex){
            assertEquals(exForTest, ex);
            verify(longParamValidatorMock).validate(RequestAlias.ACCOUNT_ID_ALIAS, DEFAULT_GAME_ID);
            verify(sumValidatorMock).validate(RequestAlias.SUM_TO_REPLENISH_ALIAS, DEFAULT_SUM);
        }
    }
}
