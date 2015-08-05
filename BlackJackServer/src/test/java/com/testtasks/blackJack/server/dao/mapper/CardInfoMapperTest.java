package com.testtasks.blackJack.server.dao.mapper;

import com.testtasks.blackJack.server.common.PlayerType;
import com.testtasks.blackJack.server.dao.object.CardInfo;
import com.testtasks.blackJack.server.domain.card.Card;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

public class CardInfoMapperTest {

    private ResultSet resultSetMock;

    private CardInfoMapper mapperForTests = new CardInfoMapper();

    private static final Card DEFAULT_CARD = Card.CRUSADE_KING;
    private static final PlayerType DEFAULT_PLAYER_TYPE = PlayerType.PLAYER;

    @Before
    public void initData(){
        resultSetMock = mock(ResultSet.class);
    }

    @Test
    public void testSuccessResult() throws SQLException {
        when(resultSetMock.getInt(CardInfoMapper.CARD_ID_ROW_ALIAS)).thenReturn(DEFAULT_CARD.getCardId());
        when(resultSetMock.getString(CardInfoMapper.PLAYER_ROW_ALIAS)).thenReturn(DEFAULT_PLAYER_TYPE.name());
        CardInfo cardInfo = mapperForTests.mapRow(resultSetMock, 0);
        assertNotNull(cardInfo);
        assertEquals(DEFAULT_PLAYER_TYPE, cardInfo.getPlayerType());
        assertEquals(DEFAULT_CARD, cardInfo.getCard());
        verify(resultSetMock).getInt(anyString());
        verify(resultSetMock).getString(anyString());
    }

    @Test
    public void testWhenGetIntThrowSQLException() throws SQLException {
        SQLException exceptionForTest = new SQLException();
        when(resultSetMock.getString(CardInfoMapper.PLAYER_ROW_ALIAS)).thenReturn(DEFAULT_PLAYER_TYPE.name());
        doThrow(exceptionForTest).when(resultSetMock).getInt(CardInfoMapper.CARD_ID_ROW_ALIAS);
        try {
            mapperForTests.mapRow(resultSetMock, 0);
            fail();
        } catch (SQLException ex) {
            assertEquals(exceptionForTest, ex);
            verify(resultSetMock).getInt(anyString());
            verify(resultSetMock).getString(anyString());
        }
    }

    @Test
    public void testWhenGetIntReturnNotValidCardId() throws SQLException {
        SQLException exceptionForTest = new SQLException();
        when(resultSetMock.getString(CardInfoMapper.PLAYER_ROW_ALIAS)).thenReturn(DEFAULT_PLAYER_TYPE.name());
        when(resultSetMock.getInt(CardInfoMapper.CARD_ID_ROW_ALIAS)).thenReturn(100);
        try {
            mapperForTests.mapRow(resultSetMock, 0);
            fail();
        } catch (SQLException ex) {
            assertNotNull(ex.getCause());
            assertTrue(ex.getCause() instanceof IllegalArgumentException);
            verify(resultSetMock).getInt(anyString());
            verify(resultSetMock).getString(anyString());
        }
    }

    @Test
    public void testWhenGetStringThrowSQLException() throws SQLException {
        SQLException exceptionForTest = new SQLException();
        doThrow(exceptionForTest).when(resultSetMock).getString(CardInfoMapper.PLAYER_ROW_ALIAS);
        try {
            mapperForTests.mapRow(resultSetMock, 0);
            fail();
        } catch (SQLException ex) {
            assertEquals(exceptionForTest, ex);
            verify(resultSetMock, never()).getInt(anyString());
            verify(resultSetMock).getString(anyString());
        }
    }

    @Test
    public void testWhenGetStringReturnNotValidPlayerType() throws SQLException {
        when(resultSetMock.getString(CardInfoMapper.PLAYER_ROW_ALIAS)).thenReturn("test");
        try {
            mapperForTests.mapRow(resultSetMock, 0);
            fail();
        } catch (SQLException ex) {
            assertNotNull(ex.getCause());
            assertTrue(ex.getCause() instanceof IllegalArgumentException);
            verify(resultSetMock, never()).getInt(anyString());
            verify(resultSetMock).getString(anyString());
        }
    }
}
