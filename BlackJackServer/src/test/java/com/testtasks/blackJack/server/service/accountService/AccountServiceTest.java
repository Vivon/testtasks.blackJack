package com.testtasks.blackJack.server.service.accountService;

import com.testtasks.blackJack.server.common.RequestAlias;
import com.testtasks.blackJack.server.dao.DaoException;
import com.testtasks.blackJack.server.dao.GameDAOI;
import com.testtasks.blackJack.server.dao.SystemDaoException;
import com.testtasks.blackJack.server.domain.account.AccountI;
import com.testtasks.blackJack.server.domain.replenish.ReplenishInfoI;
import com.testtasks.blackJack.server.exception.ServiceException;
import com.testtasks.blackJack.server.validator.LongParamValidator;
import com.testtasks.blackJack.server.validator.ParamValidationException;
import com.testtasks.blackJack.server.validator.SumRequestValidator;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    private AccountService serviceForTest = new AccountService();

    private static final Long DEFAULT_ACCOUNT_ID = 1234L;
    private static final BigDecimal DEFAULT_SUM = BigDecimal.TEN;

    private GameDAOI gameDAOMock;
    private SumRequestValidator sumRequestValidatorMock;
    private LongParamValidator longParamValidatorMock;

    private AccountI accountMock;
    private ReplenishInfoI replenishInfoMock;

    @Before
    public void initData(){
        gameDAOMock = mock(GameDAOI.class);
        sumRequestValidatorMock = mock(SumRequestValidator.class);
        longParamValidatorMock = mock(LongParamValidator.class);
        accountMock = mock(AccountI.class);
        replenishInfoMock = mock(ReplenishInfoI.class);

        serviceForTest.setLongParamValidator(longParamValidatorMock);
        serviceForTest.setServiceDAO(gameDAOMock);
        serviceForTest.setSumRequestValidator(sumRequestValidatorMock);
    }

    @Test
    public void testCreateAccount() throws DaoException, ParamValidationException {
        when(gameDAOMock.createAccount()).thenReturn(accountMock);
        assertEquals(accountMock, serviceForTest.createAccount());

        verify(gameDAOMock).createAccount();
        verify(longParamValidatorMock, never()).validate(anyString(), anyLong());
        verify(sumRequestValidatorMock, never()).validate(anyLong(), any(BigDecimal.class));
        verify(gameDAOMock, never()).getAccount(anyLong());
        verify(gameDAOMock, never()).replenishPurse(anyLong(), any(BigDecimal.class));
    }

    @Test
    public void testCreateAccountThrowDaoException() throws DaoException, ParamValidationException {
        DaoException exceptionForTest = new SystemDaoException();
        doThrow(exceptionForTest).when(gameDAOMock).createAccount();
        try {
            serviceForTest.createAccount();
            fail();
        } catch (DaoException ex){
            assertEquals(exceptionForTest, ex);
            verify(gameDAOMock).createAccount();
            verify(longParamValidatorMock, never()).validate(anyString(), anyLong());
            verify(sumRequestValidatorMock, never()).validate(anyLong(), any(BigDecimal.class));
            verify(gameDAOMock, never()).getAccount(anyLong());
            verify(gameDAOMock, never()).replenishPurse(anyLong(), any(BigDecimal.class));
        }
    }

    @Test
    public void testGetAccount() throws ServiceException {
        when(gameDAOMock.getAccount(DEFAULT_ACCOUNT_ID)).thenReturn(accountMock);
        assertEquals(accountMock, serviceForTest.getAccount(DEFAULT_ACCOUNT_ID));

        verify(gameDAOMock, never()).createAccount();
        verify(longParamValidatorMock).validate(anyString(), anyLong());
        verify(longParamValidatorMock).validate(RequestAlias.ACCOUNT_ID_ALIAS, DEFAULT_ACCOUNT_ID);
        verify(sumRequestValidatorMock, never()).validate(anyLong(), any(BigDecimal.class));
        verify(gameDAOMock).getAccount(DEFAULT_ACCOUNT_ID);
        verify(gameDAOMock).getAccount(anyLong());
        verify(gameDAOMock, never()).replenishPurse(anyLong(), any(BigDecimal.class));
    }

    @Test
    public void testGetAccountThrowParamValidationException() throws ServiceException {
        ParamValidationException exceptionForTest = new ParamValidationException(RequestAlias.ACCOUNT_ID_ALIAS);
        doThrow(exceptionForTest).when(longParamValidatorMock).validate(RequestAlias.ACCOUNT_ID_ALIAS, DEFAULT_ACCOUNT_ID);
        try {
            serviceForTest.getAccount(DEFAULT_ACCOUNT_ID);
            fail();
        } catch (ParamValidationException ex) {
            assertEquals(exceptionForTest, ex);
            verify(gameDAOMock, never()).createAccount();
            verify(longParamValidatorMock).validate(anyString(), anyLong());
            verify(longParamValidatorMock).validate(RequestAlias.ACCOUNT_ID_ALIAS, DEFAULT_ACCOUNT_ID);
            verify(sumRequestValidatorMock, never()).validate(anyLong(), any(BigDecimal.class));
            verify(gameDAOMock, never()).getAccount(anyLong());
            verify(gameDAOMock, never()).replenishPurse(anyLong(), any(BigDecimal.class));
        }
    }

    @Test
    public void testGetAccountThrowDaoException() throws ServiceException {
        DaoException exceptionForTest = new SystemDaoException();
        doThrow(exceptionForTest).when(gameDAOMock).getAccount(DEFAULT_ACCOUNT_ID);
        try {
            serviceForTest.getAccount(DEFAULT_ACCOUNT_ID);
            fail();
        } catch (DaoException ex) {
            assertEquals(exceptionForTest, ex);
            verify(gameDAOMock, never()).createAccount();
            verify(longParamValidatorMock).validate(anyString(), anyLong());
            verify(longParamValidatorMock).validate(RequestAlias.ACCOUNT_ID_ALIAS, DEFAULT_ACCOUNT_ID);
            verify(sumRequestValidatorMock, never()).validate(anyLong(), any(BigDecimal.class));
            verify(gameDAOMock).getAccount(DEFAULT_ACCOUNT_ID);
            verify(gameDAOMock).getAccount(anyLong());
            verify(gameDAOMock, never()).replenishPurse(anyLong(), any(BigDecimal.class));
        }
    }

    @Test
    public void testReplenishPurse() throws ServiceException {
        when(replenishInfoMock.getSumToReplenish()).thenReturn(DEFAULT_SUM);
        when(gameDAOMock.replenishPurse(DEFAULT_ACCOUNT_ID, DEFAULT_SUM)).thenReturn(accountMock);
        assertEquals(accountMock, serviceForTest.replenishPurse(DEFAULT_ACCOUNT_ID, replenishInfoMock));

        verify(gameDAOMock, never()).createAccount();
        verify(longParamValidatorMock, never()).validate(anyString(), anyLong());
        verify(sumRequestValidatorMock).validate(anyLong(), any(BigDecimal.class));
        verify(sumRequestValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_SUM);
        verify(gameDAOMock, never()).getAccount(anyLong());
        verify(gameDAOMock).replenishPurse(anyLong(), any(BigDecimal.class));
        verify(gameDAOMock).replenishPurse(DEFAULT_ACCOUNT_ID, DEFAULT_SUM);
    }

    @Test
    public void testReplenishPurseThrowParamValidationException() throws ServiceException {
        when(replenishInfoMock.getSumToReplenish()).thenReturn(DEFAULT_SUM);
        ParamValidationException exceptionForTest = new ParamValidationException(RequestAlias.ACCOUNT_ID_ALIAS);
        doThrow(exceptionForTest).when(sumRequestValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_SUM);
        try {
            serviceForTest.replenishPurse(DEFAULT_ACCOUNT_ID, replenishInfoMock);
            fail();
        } catch (ParamValidationException ex) {
            assertEquals(exceptionForTest, ex);
            verify(gameDAOMock, never()).createAccount();
            verify(longParamValidatorMock, never()).validate(anyString(), anyLong());
            verify(sumRequestValidatorMock).validate(anyLong(), any(BigDecimal.class));
            verify(sumRequestValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_SUM);
            verify(gameDAOMock, never()).getAccount(anyLong());
            verify(gameDAOMock, never()).replenishPurse(anyLong(), any(BigDecimal.class));
        }
    }

    @Test
    public void testReplenishPurseThrowThrowDaoException() throws ServiceException {
        when(replenishInfoMock.getSumToReplenish()).thenReturn(DEFAULT_SUM);
        DaoException exceptionForTest = new SystemDaoException();
        doThrow(exceptionForTest).when(gameDAOMock).replenishPurse(DEFAULT_ACCOUNT_ID, DEFAULT_SUM);
        try {
            serviceForTest.replenishPurse(DEFAULT_ACCOUNT_ID, replenishInfoMock);
            fail();
        } catch (DaoException ex) {
            assertEquals(exceptionForTest, ex);
            verify(gameDAOMock, never()).createAccount();
            verify(longParamValidatorMock, never()).validate(anyString(), anyLong());
            verify(sumRequestValidatorMock).validate(anyLong(), any(BigDecimal.class));
            verify(sumRequestValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_SUM);
            verify(gameDAOMock, never()).getAccount(anyLong());
            verify(gameDAOMock).replenishPurse(anyLong(), any(BigDecimal.class));
            verify(gameDAOMock).replenishPurse(DEFAULT_ACCOUNT_ID, DEFAULT_SUM);
        }
    }
}
