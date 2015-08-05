package com.testtasks.blackJack.server.service.finishGameService;

import com.testtasks.blackJack.server.common.GameState;
import com.testtasks.blackJack.server.common.WinType;
import com.testtasks.blackJack.server.common.dao.DaoGameInfoI;
import com.testtasks.blackJack.server.common.dao.HandInfoI;
import com.testtasks.blackJack.server.component.GameInfoBuilder;
import com.testtasks.blackJack.server.component.GameLogic;
import com.testtasks.blackJack.server.dao.DaoException;
import com.testtasks.blackJack.server.dao.GameDAOI;
import com.testtasks.blackJack.server.dao.SystemDaoException;
import com.testtasks.blackJack.server.domain.card.Card;
import com.testtasks.blackJack.server.domain.gameInfo.GameInfoI;
import com.testtasks.blackJack.server.domain.player.PlayerHand;
import com.testtasks.blackJack.server.exception.ServiceException;
import com.testtasks.blackJack.server.validator.HeaderParamsValidator;
import com.testtasks.blackJack.server.validator.ParamValidationException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class FinishGameServiceTest {

    private FinishGameService serviceForTests = new FinishGameService();

    private GameDAOI gameDAOMock;
    private GameInfoBuilder gameInfoBuilderMock;
    private GameLogic gameLogicMock;
    private HeaderParamsValidator headerParamsValidatorMock;

    private DaoGameInfoI daoGameInfoMock;
    private HandInfoI handInfoMock;
    private GameInfoI gameInfoMock;
    private PlayerHand playerMock;
    private PlayerHand dealerMock;
    private List<Card> cardsMock;

    private static final Long DEFAULT_ACCOUNT_ID = 12345L;
    private static final Long DEFAULT_GAME_ID = 9876L;

    private static final GameState DEFAULT_GAME_STATE = GameState.S;
    private static final WinType DEFAULT_WIN_TYPE = WinType.PLAYER_WIN;
    private static final int DEFAULT_CARDS_SIZE = 3;
    private static final BigDecimal DEFAULT_BET = BigDecimal.ONE;


    @Before
    public void initData() throws ServiceException {
        gameDAOMock = mock(GameDAOI.class);
        gameInfoBuilderMock = mock(GameInfoBuilder.class);
        gameLogicMock = mock(GameLogic.class);
        headerParamsValidatorMock = mock(HeaderParamsValidator.class);

        daoGameInfoMock = mock(DaoGameInfoI.class);
        handInfoMock = mock(HandInfoI.class);
        gameInfoMock = mock(GameInfoI.class);
        playerMock = mock(PlayerHand.class);
        dealerMock = mock(PlayerHand.class);
        cardsMock = mock(List.class);

        serviceForTests.setGameDAO(gameDAOMock);
        serviceForTests.setGameInfoBuilder(gameInfoBuilderMock);
        serviceForTests.setGameLogic(gameLogicMock);
        serviceForTests.setHeaderParamsValidator(headerParamsValidatorMock);

        when(gameDAOMock.getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID)).thenReturn(daoGameInfoMock);
        when(gameDAOMock.getHandInfo(DEFAULT_GAME_ID)).thenReturn(handInfoMock);
        when(gameInfoBuilderMock.build(daoGameInfoMock, handInfoMock)).thenReturn(gameInfoMock);

        when(gameInfoMock.getState()).thenReturn(DEFAULT_GAME_STATE);
        when(gameInfoMock.getPlayer()).thenReturn(playerMock);
        when(gameInfoMock.getDealer()).thenReturn(dealerMock);
        when(gameInfoMock.getBet()).thenReturn(DEFAULT_BET);

        when(playerMock.getHand()).thenReturn(cardsMock);
        when(cardsMock.size()).thenReturn(DEFAULT_CARDS_SIZE);
        when(gameLogicMock.getWinner(playerMock, dealerMock)).thenReturn(DEFAULT_WIN_TYPE);
    }

    @Test
    public void testSuccess() throws ServiceException {
        assertEquals(gameInfoMock, serviceForTests.finishGame(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID));

        verify(headerParamsValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
        verify(gameDAOMock).getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID);
        verify(gameDAOMock).getHandInfo(DEFAULT_GAME_ID);
        verify(gameInfoBuilderMock).build(daoGameInfoMock, handInfoMock);
        verify(gameInfoMock).getState();
        verify(gameInfoMock, atLeastOnce()).getPlayer();
        verify(gameInfoMock).getDealer();
        verify(gameLogicMock).getWinner(playerMock, dealerMock);
        verify(gameDAOMock).updateGameState(DEFAULT_GAME_ID, GameState.F);
        verify(gameDAOMock).replenishPurse(DEFAULT_ACCOUNT_ID, DEFAULT_BET);
    }

    @Test
    public void testWhenHeaderParamsValidatorThrowException() throws ServiceException {
        ParamValidationException exceptionForTest = new ParamValidationException("");
        doThrow(exceptionForTest).when(headerParamsValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
        try {
            serviceForTests.finishGame(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            fail();
        } catch (ParamValidationException ex){
            assertEquals(exceptionForTest, ex);

            verify(headerParamsValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            verify(gameDAOMock, never()).getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID);
            verify(gameDAOMock, never()).getHandInfo(DEFAULT_GAME_ID);
            verify(gameInfoBuilderMock, never()).build(daoGameInfoMock, handInfoMock);
            verify(gameInfoMock, never()).getState();
            verify(gameInfoMock, never()).getPlayer();
            verify(gameInfoMock, never()).getDealer();
            verify(gameLogicMock, never()).getWinner(playerMock, dealerMock);
            verify(gameDAOMock, never()).updateGameState(DEFAULT_GAME_ID, GameState.F);
            verify(gameDAOMock, never()).replenishPurse(DEFAULT_ACCOUNT_ID, DEFAULT_BET);
        }
    }

    @Test
    public void testWhenGetGameInfoThrowException() throws ServiceException {
        DaoException exceptionForTest = new SystemDaoException();
        doThrow(exceptionForTest).when(gameDAOMock).getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID);
        try {
            serviceForTests.finishGame(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            fail();
        } catch (SystemDaoException ex){
            assertEquals(exceptionForTest, ex);

            verify(headerParamsValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            verify(gameDAOMock).getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID);
            verify(gameDAOMock, never()).getHandInfo(DEFAULT_GAME_ID);
            verify(gameInfoBuilderMock, never()).build(daoGameInfoMock, handInfoMock);
            verify(gameInfoMock, never()).getState();
            verify(gameInfoMock, never()).getPlayer();
            verify(gameInfoMock, never()).getDealer();
            verify(gameLogicMock, never()).getWinner(playerMock, dealerMock);
            verify(gameDAOMock, never()).updateGameState(DEFAULT_GAME_ID, GameState.F);
            verify(gameDAOMock, never()).replenishPurse(DEFAULT_ACCOUNT_ID, DEFAULT_BET);
        }
    }

    @Test
    public void testWhenGetHandInfoThrowException() throws ServiceException {
        DaoException exceptionForTest = new SystemDaoException();
        doThrow(exceptionForTest).when(gameDAOMock).getHandInfo(DEFAULT_GAME_ID);
        try {
            serviceForTests.finishGame(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            fail();
        } catch (SystemDaoException ex){
            assertEquals(exceptionForTest, ex);

            verify(headerParamsValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            verify(gameDAOMock).getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID);
            verify(gameDAOMock).getHandInfo(DEFAULT_GAME_ID);
            verify(gameInfoBuilderMock, never()).build(daoGameInfoMock, handInfoMock);
            verify(gameInfoMock, never()).getState();
            verify(gameInfoMock, never()).getPlayer();
            verify(gameInfoMock, never()).getDealer();
            verify(gameLogicMock, never()).getWinner(playerMock, dealerMock);
            verify(gameDAOMock, never()).updateGameState(DEFAULT_GAME_ID, GameState.F);
            verify(gameDAOMock, never()).replenishPurse(DEFAULT_ACCOUNT_ID, DEFAULT_BET);
        }
    }

    @Test
    public void testWhenUpdateGameStateThrowException() throws ServiceException {
        DaoException exceptionForTest = new SystemDaoException();
        doThrow(exceptionForTest).when(gameDAOMock).updateGameState(DEFAULT_GAME_ID, GameState.F);
        try {
            serviceForTests.finishGame(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            fail();
        } catch (SystemDaoException ex){
            assertEquals(exceptionForTest, ex);

            verify(headerParamsValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            verify(gameDAOMock).getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID);
            verify(gameDAOMock).getHandInfo(DEFAULT_GAME_ID);
            verify(gameInfoBuilderMock).build(daoGameInfoMock, handInfoMock);
            verify(gameInfoMock).getState();
            verify(gameInfoMock).getPlayer();
            verify(gameInfoMock).getDealer();
            verify(gameLogicMock).getWinner(playerMock, dealerMock);
            verify(gameDAOMock).updateGameState(DEFAULT_GAME_ID, GameState.F);
            verify(gameDAOMock, never()).replenishPurse(DEFAULT_ACCOUNT_ID, DEFAULT_BET);
        }
    }

    @Test
    public void testWhenReplenishPurseThrowException() throws ServiceException {
        DaoException exceptionForTest = new SystemDaoException();
        doThrow(exceptionForTest).when(gameDAOMock).replenishPurse(DEFAULT_ACCOUNT_ID, DEFAULT_BET);
        try {
            serviceForTests.finishGame(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            fail();
        } catch (SystemDaoException ex){
            assertEquals(exceptionForTest, ex);

            verify(headerParamsValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            verify(gameDAOMock).getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID);
            verify(gameDAOMock).getHandInfo(DEFAULT_GAME_ID);
            verify(gameInfoBuilderMock).build(daoGameInfoMock, handInfoMock);
            verify(gameInfoMock).getState();
            verify(gameInfoMock, atLeastOnce()).getPlayer();
            verify(gameInfoMock).getDealer();
            verify(gameLogicMock).getWinner(playerMock, dealerMock);
            verify(gameDAOMock).updateGameState(DEFAULT_GAME_ID, GameState.F);
            verify(gameDAOMock).replenishPurse(DEFAULT_ACCOUNT_ID, DEFAULT_BET);
        }
    }

    @Test
    public void testSuccessWhenStateIsFinish() throws ServiceException {
        when(gameInfoMock.getState()).thenReturn(GameState.F);
        assertEquals(gameInfoMock, serviceForTests.finishGame(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID));

        verify(headerParamsValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
        verify(gameDAOMock).getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID);
        verify(gameDAOMock).getHandInfo(DEFAULT_GAME_ID);
        verify(gameInfoBuilderMock).build(daoGameInfoMock, handInfoMock);
        verify(gameInfoMock).getState();
        verify(gameInfoMock, never()).getPlayer();
        verify(gameInfoMock, never()).getDealer();
        verify(gameLogicMock, never()).getWinner(playerMock, dealerMock);
        verify(gameDAOMock, never()).updateGameState(DEFAULT_GAME_ID, GameState.F);
        verify(gameDAOMock, never()).replenishPurse(DEFAULT_ACCOUNT_ID, DEFAULT_BET);
    }

    @Test
    public void testSuccessWhenPlayerNotWin() throws ServiceException {
        when(gameLogicMock.getWinner(playerMock, dealerMock)).thenReturn(WinType.DEALER_WIN);
        assertEquals(gameInfoMock, serviceForTests.finishGame(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID));

        verify(headerParamsValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
        verify(gameDAOMock).getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID);
        verify(gameDAOMock).getHandInfo(DEFAULT_GAME_ID);
        verify(gameInfoBuilderMock).build(daoGameInfoMock, handInfoMock);
        verify(gameInfoMock).getState();
        verify(gameInfoMock, atLeastOnce()).getPlayer();
        verify(gameInfoMock).getDealer();
        verify(gameLogicMock).getWinner(playerMock, dealerMock);
        verify(gameDAOMock).updateGameState(DEFAULT_GAME_ID, GameState.F);
        verify(gameDAOMock, never()).replenishPurse(DEFAULT_ACCOUNT_ID, DEFAULT_BET);
    }

    @Test
    public void testSuccessWhenPlayerNotWinWithBlackJack() throws ServiceException {
        when(cardsMock.size()).thenReturn(2);
        assertEquals(gameInfoMock, serviceForTests.finishGame(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID));

        verify(headerParamsValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
        verify(gameDAOMock).getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID);
        verify(gameDAOMock).getHandInfo(DEFAULT_GAME_ID);
        verify(gameInfoBuilderMock).build(daoGameInfoMock, handInfoMock);
        verify(gameInfoMock).getState();
        verify(gameInfoMock, atLeastOnce()).getPlayer();
        verify(gameInfoMock).getDealer();
        verify(gameLogicMock).getWinner(playerMock, dealerMock);
        verify(gameDAOMock).updateGameState(DEFAULT_GAME_ID, GameState.F);
        verify(gameDAOMock).replenishPurse(DEFAULT_ACCOUNT_ID, DEFAULT_BET.multiply(BigDecimal.valueOf(1.5)));
    }
}
