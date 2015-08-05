package com.testtasks.blackJack.server.service.getCardService;

import com.testtasks.blackJack.server.common.GameState;
import com.testtasks.blackJack.server.common.PlayerType;
import com.testtasks.blackJack.server.common.dao.DaoGameInfoI;
import com.testtasks.blackJack.server.common.dao.HandInfoI;
import com.testtasks.blackJack.server.component.CardGenerator;
import com.testtasks.blackJack.server.component.GameInfoBuilder;
import com.testtasks.blackJack.server.component.GameLogic;
import com.testtasks.blackJack.server.dao.DaoException;
import com.testtasks.blackJack.server.dao.GameDAOI;
import com.testtasks.blackJack.server.dao.SystemDaoException;
import com.testtasks.blackJack.server.dao.object.CardInfo;
import com.testtasks.blackJack.server.domain.card.Card;
import com.testtasks.blackJack.server.domain.gameInfo.GameInfoI;
import com.testtasks.blackJack.server.exception.NotValidGameStateException;
import com.testtasks.blackJack.server.exception.ServiceException;
import com.testtasks.blackJack.server.validator.HeaderParamsValidator;
import com.testtasks.blackJack.server.validator.ParamValidationException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class GetCardServiceTest {

    private GetCardService serviceForTest = new GetCardService();

    private GameDAOI gameDAOMock;
    private CardGenerator cardGeneratorMock;
    private GameInfoBuilder gameInfoBuilderMock;
    private GameLogic gameLogicMock;
    private HeaderParamsValidator headerParamsValidatorMock;

    private DaoGameInfoI daoGameInfoMock;
    private HandInfoI handInfoMock;
    private GameInfoI gameInfoMock;

    private ArrayList<CardInfo> defaultPlayerHandCards;
    private ArrayList<Card> expectedCards;

    private static final Long DEFAULT_ACCOUNT_ID = 1234L;
    private static final Long DEFAULT_GAME_ID = 4567L;
    private static final GameState DEFAULT_GAME_STATE = GameState.D;
    private static final Card DEFAULT_GENERATED_CARD = Card.DIAMONDS_TEN;
    private static final CardInfo DEFAULT_CARD_INFO = new CardInfo(DEFAULT_GENERATED_CARD, PlayerType.PLAYER);
    private static final CardInfo DEFAULT_DEALER_CARD_INFO = new CardInfo(DEFAULT_GENERATED_CARD, PlayerType.DEALER);


    @Before
    public void initData() throws ServiceException {
        gameDAOMock = mock(GameDAOI.class);
        cardGeneratorMock = mock(CardGenerator.class);
        gameInfoBuilderMock = mock(GameInfoBuilder.class);
        gameLogicMock = mock(GameLogic.class);
        headerParamsValidatorMock = mock(HeaderParamsValidator.class);

        daoGameInfoMock = mock(DaoGameInfoI.class);
        handInfoMock = mock(HandInfoI.class);
        gameInfoMock = mock(GameInfoI.class);

        defaultPlayerHandCards = new ArrayList<CardInfo>(){{
            add(new CardInfo(Card.CRUSADE_ACE, PlayerType.PLAYER));
            add(new CardInfo(Card.CRUSADE_NINE, PlayerType.DEALER));
        }};

        expectedCards = new ArrayList<Card>(){{
            add(Card.CRUSADE_ACE);
        }};

        serviceForTest.setGameInfoBuilder(gameInfoBuilderMock);
        serviceForTest.setGameLogic(gameLogicMock);
        serviceForTest.setHeaderParamsValidator(headerParamsValidatorMock);
        serviceForTest.setGameDAO(gameDAOMock);
        serviceForTest.setCardGenerator(cardGeneratorMock);

        when(gameDAOMock.getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID)).thenReturn(daoGameInfoMock);
        when(gameDAOMock.getHandInfo(DEFAULT_GAME_ID)).thenReturn(handInfoMock);
        when(gameInfoBuilderMock.build(daoGameInfoMock, handInfoMock)).thenReturn(gameInfoMock);

        when(handInfoMock.getCards()).thenReturn(defaultPlayerHandCards);

        when(daoGameInfoMock.getState()).thenReturn(DEFAULT_GAME_STATE);
        when(gameLogicMock.isPlayerNotHaveBust(expectedCards)).thenReturn(true);

        when(daoGameInfoMock.getGameId()).thenReturn(DEFAULT_GAME_ID);

        when(cardGeneratorMock.generate(handInfoMock)).thenReturn(DEFAULT_GENERATED_CARD);
    }

    @Test
    public void testSuccess() throws ServiceException {
        assertEquals(gameInfoMock, serviceForTest.getCard(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID));

        verify(headerParamsValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
        verify(gameDAOMock).getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID);
        verify(gameDAOMock).getHandInfo(DEFAULT_GAME_ID);
        verify(cardGeneratorMock).generate(handInfoMock);
        verify(gameDAOMock).saveCardsInfo(DEFAULT_GAME_ID, DEFAULT_CARD_INFO);
        verify(handInfoMock).addCard(DEFAULT_CARD_INFO);
    }

    @Test
    public void testWhenHeaderParamsValidatorThrowParamValidationException() throws ServiceException {
        ParamValidationException exceptionForTest = new ParamValidationException("");
        doThrow(exceptionForTest).when(headerParamsValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);

        try{
            serviceForTest.getCard(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            fail();
        } catch (ParamValidationException ex){
            assertEquals(exceptionForTest, ex);
            verify(headerParamsValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            verify(gameDAOMock, never()).getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID);
            verify(gameDAOMock, never()).getHandInfo(DEFAULT_GAME_ID);
            verify(cardGeneratorMock, never()).generate(handInfoMock);
            verify(gameDAOMock, never()).saveCardsInfo(DEFAULT_GAME_ID, DEFAULT_CARD_INFO);
            verify(handInfoMock, never()).addCard(DEFAULT_CARD_INFO);
        }
    }

    @Test
    public void testWhenGetGameInfoThrowParamValidationException() throws ServiceException {
        DaoException exceptionForTest = new SystemDaoException();
        doThrow(exceptionForTest).when(gameDAOMock).getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID);

        try{
            serviceForTest.getCard(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            fail();
        } catch (SystemDaoException ex){
            assertEquals(exceptionForTest, ex);
            verify(headerParamsValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            verify(gameDAOMock).getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID);
            verify(gameDAOMock, never()).getHandInfo(DEFAULT_GAME_ID);
            verify(cardGeneratorMock, never()).generate(handInfoMock);
            verify(gameDAOMock, never()).saveCardsInfo(DEFAULT_GAME_ID, DEFAULT_CARD_INFO);
            verify(handInfoMock, never()).addCard(DEFAULT_CARD_INFO);
        }
    }

    @Test
    public void testWhenGetHandInfoThrowParamValidationException() throws ServiceException {
        DaoException exceptionForTest = new SystemDaoException();
        doThrow(exceptionForTest).when(gameDAOMock).getHandInfo(DEFAULT_GAME_ID);

        try{
            serviceForTest.getCard(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            fail();
        } catch (SystemDaoException ex){
            assertEquals(exceptionForTest, ex);
            verify(headerParamsValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            verify(gameDAOMock).getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID);
            verify(gameDAOMock).getHandInfo(DEFAULT_GAME_ID);
            verify(cardGeneratorMock, never()).generate(handInfoMock);
            verify(gameDAOMock, never()).saveCardsInfo(DEFAULT_GAME_ID, DEFAULT_CARD_INFO);
            verify(handInfoMock, never()).addCard(DEFAULT_CARD_INFO);
        }
    }

    @Test
    public void testWhenSaveCardsInfoThrowParamValidationException() throws ServiceException {
        DaoException exceptionForTest = new SystemDaoException();
        doThrow(exceptionForTest).when(gameDAOMock).saveCardsInfo(DEFAULT_GAME_ID, DEFAULT_CARD_INFO);
        try{
            serviceForTest.getCard(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            fail();
        } catch (SystemDaoException ex){
            assertEquals(exceptionForTest, ex);
            verify(headerParamsValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            verify(gameDAOMock).getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID);
            verify(gameDAOMock).getHandInfo(DEFAULT_GAME_ID);
            verify(cardGeneratorMock).generate(handInfoMock);
            verify(gameDAOMock).saveCardsInfo(DEFAULT_GAME_ID, DEFAULT_CARD_INFO);
            verify(handInfoMock, never()).addCard(DEFAULT_CARD_INFO);
        }
    }

    @Test
    public void testWhenStateEThrowParamValidationException() throws ServiceException {
        when(daoGameInfoMock.getState()).thenReturn(GameState.E);
        try{
            serviceForTest.getCard(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            fail();
        } catch (NotValidGameStateException ex){
            verify(headerParamsValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
            verify(gameDAOMock).getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID);
            verify(gameDAOMock).getHandInfo(DEFAULT_GAME_ID);
            verify(cardGeneratorMock, never()).generate(handInfoMock);
            verify(gameDAOMock, never()).saveCardsInfo(DEFAULT_GAME_ID, DEFAULT_CARD_INFO);
            verify(handInfoMock, never()).addCard(DEFAULT_CARD_INFO);
        }
    }

    @Test
    public void testWhenPlayerTypeIsDealerThrowParamValidationException() throws ServiceException {
        when(daoGameInfoMock.getState()).thenReturn(GameState.S);
        expectedCards = new ArrayList<Card>(){{
            add(Card.CRUSADE_NINE);
        }};
        when(gameLogicMock.isDealerNotHaveMaxScore(expectedCards)).thenReturn(true);

        assertEquals(gameInfoMock, serviceForTest.getCard(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID));

        verify(headerParamsValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_GAME_ID);
        verify(gameDAOMock).getGameInfo(DEFAULT_GAME_ID, DEFAULT_ACCOUNT_ID);
        verify(gameDAOMock).getHandInfo(DEFAULT_GAME_ID);
        verify(cardGeneratorMock).generate(handInfoMock);
        verify(gameDAOMock).saveCardsInfo(DEFAULT_GAME_ID, DEFAULT_DEALER_CARD_INFO);
        verify(handInfoMock).addCard(DEFAULT_CARD_INFO);
    }
}
