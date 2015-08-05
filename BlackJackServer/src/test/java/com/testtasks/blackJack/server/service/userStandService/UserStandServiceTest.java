package com.testtasks.blackJack.server.service.userStandService;

import com.testtasks.blackJack.server.common.GameState;
import com.testtasks.blackJack.server.common.dao.DaoGameInfoI;
import com.testtasks.blackJack.server.common.dao.HandInfoI;
import com.testtasks.blackJack.server.component.GameInfoBuilder;
import com.testtasks.blackJack.server.dao.DaoException;
import com.testtasks.blackJack.server.dao.GameDAOI;
import com.testtasks.blackJack.server.dao.SystemDaoException;
import com.testtasks.blackJack.server.domain.gameInfo.GameInfo;
import com.testtasks.blackJack.server.domain.gameInfo.GameInfoI;
import com.testtasks.blackJack.server.exception.NotValidGameStateException;
import com.testtasks.blackJack.server.exception.ServiceException;
import com.testtasks.blackJack.server.service.userStandService.UserStandService;
import com.testtasks.blackJack.server.validator.HeaderParamsValidator;
import com.testtasks.blackJack.server.validator.ParamValidationException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

public class UserStandServiceTest {

    private UserStandService serviceForTest = new UserStandService();

    private GameDAOI gameDAOMock;
    private GameInfoBuilder gameInfoBuilderMock;
    private HeaderParamsValidator headerParamsValidatorMock;

    private DaoGameInfoI daoGameInfoMock;
    private HandInfoI handInfoMock;
    private GameInfoI gameInfoMock;

    private static final Long DEFAULT_ACCOUNT_ID = 123L;
    private static final Long DEFAULT_GAME_ID = 5678L;

    @Before
    public void initData(){
        gameDAOMock = mock(GameDAOI.class);
        gameInfoBuilderMock = mock(GameInfoBuilder.class);
        headerParamsValidatorMock = mock(HeaderParamsValidator.class);

        daoGameInfoMock = mock(DaoGameInfoI.class);
        handInfoMock = mock(HandInfoI.class);
        gameInfoMock = mock(GameInfoI.class);

        serviceForTest.setGameDAO(gameDAOMock);
        serviceForTest.setGameInfoBuilder(gameInfoBuilderMock);
        serviceForTest.setHeaderParamsValidator(headerParamsValidatorMock);
    }

    @Test
    public void testSuccess() throws ServiceException {
        when(gameDAOMock.getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID)).thenReturn(daoGameInfoMock);
        when(daoGameInfoMock.getState()).thenReturn(GameState.D);
        when(gameDAOMock.getHandInfo(DEFAULT_GAME_ID)).thenReturn(handInfoMock);
        when(gameInfoBuilderMock.build(daoGameInfoMock, handInfoMock)).thenReturn(gameInfoMock);

        assertEquals(gameInfoMock, serviceForTest.userStand(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID));

        verify(headerParamsValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
        verify(headerParamsValidatorMock).validate(anyLong(), anyLong());
        verify(gameDAOMock).getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID);
        verify(gameDAOMock).getGameInfo(anyLong(), anyLong());
        verify(gameDAOMock).getHandInfo(DEFAULT_GAME_ID);
        verify(gameDAOMock).getHandInfo(anyLong());
        verify(daoGameInfoMock).getState();
        verify(gameDAOMock).updateGameState(DEFAULT_GAME_ID, GameState.S);
        verify(gameDAOMock).updateGameState(anyLong(), any(GameState.class));
        verify(gameInfoBuilderMock).build(daoGameInfoMock, handInfoMock);
        verify(gameInfoBuilderMock).build(any(DaoGameInfoI.class), any(HandInfoI.class));
    }

    @Test
    public void testWhenParamValidationException() throws ServiceException {
        ParamValidationException exceptionForTest = new ParamValidationException("");
        doThrow(exceptionForTest).when(headerParamsValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);

        try {
            serviceForTest.userStand(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            fail();
        } catch (ParamValidationException ex) {
            assertEquals(exceptionForTest, ex);
            verify(headerParamsValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            verify(headerParamsValidatorMock).validate(anyLong(), anyLong());
            verify(gameDAOMock, never()).getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID);
            verify(gameDAOMock, never()).getGameInfo(anyLong(), anyLong());
            verify(gameDAOMock, never()).getHandInfo(DEFAULT_GAME_ID);
            verify(gameDAOMock, never()).getHandInfo(anyLong());
            verify(daoGameInfoMock, never()).getState();
            verify(gameDAOMock, never()).updateGameState(DEFAULT_GAME_ID, GameState.S);
            verify(gameDAOMock, never()).updateGameState(anyLong(), any(GameState.class));
            verify(gameInfoBuilderMock, never()).build(daoGameInfoMock, handInfoMock);
            verify(gameInfoBuilderMock, never()).build(any(DaoGameInfoI.class), any(HandInfoI.class));
        }
    }

    @Test
    public void testWhenGetGameInfoThrowDaoException() throws ServiceException {
        DaoException exceptionForTest = new SystemDaoException();
        doThrow(exceptionForTest).when(gameDAOMock).getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID);

        try {
            serviceForTest.userStand(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            fail();
        } catch (DaoException ex) {
            assertEquals(exceptionForTest, ex);
            verify(headerParamsValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            verify(headerParamsValidatorMock).validate(anyLong(), anyLong());
            verify(gameDAOMock).getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID);
            verify(gameDAOMock).getGameInfo(anyLong(), anyLong());
            verify(gameDAOMock, never()).getHandInfo(DEFAULT_GAME_ID);
            verify(gameDAOMock, never()).getHandInfo(anyLong());
            verify(daoGameInfoMock, never()).getState();
            verify(gameDAOMock, never()).updateGameState(DEFAULT_GAME_ID, GameState.S);
            verify(gameDAOMock, never()).updateGameState(anyLong(), any(GameState.class));
            verify(gameInfoBuilderMock, never()).build(daoGameInfoMock, handInfoMock);
            verify(gameInfoBuilderMock, never()).build(any(DaoGameInfoI.class), any(HandInfoI.class));
        }
    }

    @Test
    public void testWhenGetHandInfoThrowDaoException() throws ServiceException {
        when(gameDAOMock.getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID)).thenReturn(daoGameInfoMock);
        DaoException exceptionForTest = new SystemDaoException();
        doThrow(exceptionForTest).when(gameDAOMock).getHandInfo(DEFAULT_GAME_ID);

        try {
            serviceForTest.userStand(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            fail();
        } catch (DaoException ex) {
            assertEquals(exceptionForTest, ex);
            verify(headerParamsValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            verify(headerParamsValidatorMock).validate(anyLong(), anyLong());
            verify(gameDAOMock).getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID);
            verify(gameDAOMock).getGameInfo(anyLong(), anyLong());
            verify(gameDAOMock).getHandInfo(DEFAULT_GAME_ID);
            verify(gameDAOMock).getHandInfo(anyLong());
            verify(daoGameInfoMock, never()).getState();
            verify(gameDAOMock, never()).updateGameState(DEFAULT_GAME_ID, GameState.S);
            verify(gameDAOMock, never()).updateGameState(anyLong(), any(GameState.class));
            verify(gameInfoBuilderMock, never()).build(daoGameInfoMock, handInfoMock);
            verify(gameInfoBuilderMock, never()).build(any(DaoGameInfoI.class), any(HandInfoI.class));
        }
    }

    @Test
    public void testWhenGameStateNotValid() throws ServiceException {
        when(gameDAOMock.getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID)).thenReturn(daoGameInfoMock);
        when(gameDAOMock.getHandInfo(DEFAULT_GAME_ID)).thenReturn(handInfoMock);
        when(daoGameInfoMock.getState()).thenReturn(GameState.S);

        try {
            serviceForTest.userStand(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            fail();
        } catch (NotValidGameStateException ex) {
            assertEquals("Не доступное действие для данной игры", ex.getMessage());
            verify(headerParamsValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            verify(headerParamsValidatorMock).validate(anyLong(), anyLong());
            verify(gameDAOMock).getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID);
            verify(gameDAOMock).getGameInfo(anyLong(), anyLong());
            verify(gameDAOMock).getHandInfo(DEFAULT_GAME_ID);
            verify(gameDAOMock).getHandInfo(anyLong());
            verify(daoGameInfoMock).getState();
            verify(gameDAOMock, never()).updateGameState(DEFAULT_GAME_ID, GameState.S);
            verify(gameDAOMock, never()).updateGameState(anyLong(), any(GameState.class));
            verify(gameInfoBuilderMock, never()).build(daoGameInfoMock, handInfoMock);
            verify(gameInfoBuilderMock, never()).build(any(DaoGameInfoI.class), any(HandInfoI.class));
        }
    }

    @Test
    public void testWhenUpdateGameStateThrowDaoException() throws ServiceException {
        when(gameDAOMock.getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID)).thenReturn(daoGameInfoMock);
        when(gameDAOMock.getHandInfo(DEFAULT_GAME_ID)).thenReturn(handInfoMock);
        when(daoGameInfoMock.getState()).thenReturn(GameState.D);
        DaoException exceptionForTest = new SystemDaoException();
        doThrow(exceptionForTest).when(gameDAOMock).updateGameState(DEFAULT_GAME_ID, GameState.S);

        try {
            serviceForTest.userStand(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            fail();
        } catch (DaoException ex) {
            assertEquals(exceptionForTest, ex);
            verify(headerParamsValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            verify(headerParamsValidatorMock).validate(anyLong(), anyLong());
            verify(gameDAOMock).getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID);
            verify(gameDAOMock).getGameInfo(anyLong(), anyLong());
            verify(gameDAOMock).getHandInfo(DEFAULT_GAME_ID);
            verify(gameDAOMock).getHandInfo(anyLong());
            verify(daoGameInfoMock).getState();
            verify(gameDAOMock).updateGameState(DEFAULT_GAME_ID, GameState.S);
            verify(gameDAOMock).updateGameState(anyLong(), any(GameState.class));
            verify(gameInfoBuilderMock, never()).build(daoGameInfoMock, handInfoMock);
            verify(gameInfoBuilderMock, never()).build(any(DaoGameInfoI.class), any(HandInfoI.class));
        }
    }
}
