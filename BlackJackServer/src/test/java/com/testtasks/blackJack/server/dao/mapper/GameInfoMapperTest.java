package com.testtasks.blackJack.server.dao.mapper;

import com.testtasks.blackJack.server.common.GameState;
import com.testtasks.blackJack.server.dao.object.DaoGameInfo;
import com.testtasks.blackJack.server.domain.account.Account;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

public class GameInfoMapperTest {

    private ResultSet resultSetMock;

    private GameInfoMapper mapperForTests = new GameInfoMapper();

    private static final Long DEFAULT_GAME_ID = 1l;
    private static final BigDecimal DEFAULT_BET = BigDecimal.TEN;
    private static final GameState DEFAULT_GAME_STATE = GameState.D;

    @Before
    public void initData(){
        resultSetMock = mock(ResultSet.class);
    }

    @Test
    public void testSuccessResult() throws SQLException {
        when(resultSetMock.getLong(GameInfoMapper.GAME_ID_ROW_ALIAS)).thenReturn(DEFAULT_GAME_ID);
        when(resultSetMock.getBigDecimal(GameInfoMapper.BET_ROW_ALIAS)).thenReturn(DEFAULT_BET);
        when(resultSetMock.getString(GameInfoMapper.STATE_ROW_ALIAS)).thenReturn(DEFAULT_GAME_STATE.getStateDBAlias()
                .toString());

        DaoGameInfo daoGameInfo = mapperForTests.mapRow(resultSetMock, 0);
        assertNotNull(daoGameInfo);
        assertEquals(DEFAULT_GAME_ID, daoGameInfo.getGameId());
        assertEquals(DEFAULT_BET, daoGameInfo.getBet());
        assertEquals(DEFAULT_GAME_STATE, daoGameInfo.getState());
        verify(resultSetMock).getLong(anyString());
        verify(resultSetMock).getBigDecimal(anyString());
        verify(resultSetMock).getString(anyString());
    }

    @Test
    public void testWhenGetLongThrowSQLException() throws SQLException {
        SQLException exceptionForTest = new SQLException();
        doThrow(exceptionForTest).when(resultSetMock).getLong(GameInfoMapper.GAME_ID_ROW_ALIAS);
        try {
            mapperForTests.mapRow(resultSetMock, 0);
            fail();
        } catch (SQLException ex) {
            assertEquals(exceptionForTest, ex);
            verify(resultSetMock).getLong(anyString());
            verify(resultSetMock, never()).getBigDecimal(anyString());
            verify(resultSetMock, never()).getString(anyString());
        }
    }

    @Test
    public void testWhenGetBigDecimalThrowSQLException() throws SQLException {
        SQLException exceptionForTest = new SQLException();
        when(resultSetMock.getLong(GameInfoMapper.GAME_ID_ROW_ALIAS)).thenReturn(DEFAULT_GAME_ID);
        doThrow(exceptionForTest).when(resultSetMock).getBigDecimal(GameInfoMapper.BET_ROW_ALIAS);
        try {
            mapperForTests.mapRow(resultSetMock, 0);
            fail();
        } catch (SQLException ex) {
            assertEquals(exceptionForTest, ex);
            verify(resultSetMock).getLong(anyString());
            verify(resultSetMock).getBigDecimal(anyString());
            verify(resultSetMock, never()).getString(anyString());
        }
    }

    @Test
    public void testWhenGetStringThrowSQLException() throws SQLException {
        SQLException exceptionForTest = new SQLException();
        when(resultSetMock.getLong(GameInfoMapper.GAME_ID_ROW_ALIAS)).thenReturn(DEFAULT_GAME_ID);
        when(resultSetMock.getBigDecimal(GameInfoMapper.BET_ROW_ALIAS)).thenReturn(DEFAULT_BET);
        doThrow(exceptionForTest).when(resultSetMock).getString(GameInfoMapper.STATE_ROW_ALIAS);
        try {
            mapperForTests.mapRow(resultSetMock, 0);
            fail();
        } catch (SQLException ex) {
            assertEquals(exceptionForTest, ex);
            verify(resultSetMock).getLong(anyString());
            verify(resultSetMock).getBigDecimal(anyString());
            verify(resultSetMock).getString(anyString());
        }
    }

    @Test
    public void testWhenGetStringReturnNotValidDBAlias() throws SQLException {
        when(resultSetMock.getLong(GameInfoMapper.GAME_ID_ROW_ALIAS)).thenReturn(DEFAULT_GAME_ID);
        when(resultSetMock.getBigDecimal(GameInfoMapper.BET_ROW_ALIAS)).thenReturn(DEFAULT_BET);
        when(resultSetMock.getString(GameInfoMapper.STATE_ROW_ALIAS)).thenReturn("A");
        try {
            mapperForTests.mapRow(resultSetMock, 0);
            fail();
        } catch (SQLException ex) {
            assertNotNull(ex.getCause());
            assertTrue(ex.getCause() instanceof IllegalArgumentException);
            verify(resultSetMock).getLong(anyString());
            verify(resultSetMock).getBigDecimal(anyString());
            verify(resultSetMock).getString(anyString());
        }
    }

}
