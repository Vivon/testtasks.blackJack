package com.testtasks.blackJack.server.dao.mapper;

import com.testtasks.blackJack.server.domain.account.Account;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class AccountMapperTest {

    private ResultSet resultSetMock;

    private AccountMapper mapperForTests = new AccountMapper();

    private static final Long DEFAULT_ACCOUNT_ID = 1l;
    private static final BigDecimal DEFAULT_SUM = BigDecimal.TEN;

    @Before
    public void initData(){
        resultSetMock = mock(ResultSet.class);
    }

    @Test
    public void testSuccessResult() throws SQLException {
        when(resultSetMock.getLong(AccountMapper.ACCOUNT_ID_ROW_ALIAS)).thenReturn(DEFAULT_ACCOUNT_ID);
        when(resultSetMock.getBigDecimal(AccountMapper.ACCOUNT_SUM_ROW_ALIAS)).thenReturn(DEFAULT_SUM);
        Account account = mapperForTests.mapRow(resultSetMock, 0);
        assertNotNull(account);
        assertEquals(DEFAULT_ACCOUNT_ID, account.getAccountId());
        assertEquals(DEFAULT_SUM, account.getAccountSum());
        verify(resultSetMock).getLong(anyString());
        verify(resultSetMock).getBigDecimal(anyString());
    }

    @Test
    public void testWhenGetLongThrowSQLException() throws SQLException {
        SQLException exceptionForTest = new SQLException();
        doThrow(exceptionForTest).when(resultSetMock).getLong(AccountMapper.ACCOUNT_ID_ROW_ALIAS);
        try {
            mapperForTests.mapRow(resultSetMock, 0);
            fail();
        } catch (SQLException ex) {
            assertEquals(exceptionForTest, ex);
            verify(resultSetMock).getLong(anyString());
            verify(resultSetMock, never()).getBigDecimal(anyString());
        }
    }

    @Test
    public void testWhenGetBigDecimalThrowSQLException() throws SQLException {
        SQLException exceptionForTest = new SQLException();
        when(resultSetMock.getLong(AccountMapper.ACCOUNT_ID_ROW_ALIAS)).thenReturn(DEFAULT_ACCOUNT_ID);
        doThrow(exceptionForTest).when(resultSetMock).getBigDecimal(AccountMapper.ACCOUNT_SUM_ROW_ALIAS);
        try {
            mapperForTests.mapRow(resultSetMock, 0);
            fail();
        } catch (SQLException ex) {
            assertEquals(exceptionForTest, ex);
            verify(resultSetMock).getLong(anyString());
            verify(resultSetMock).getBigDecimal(anyString());
        }
    }
}
