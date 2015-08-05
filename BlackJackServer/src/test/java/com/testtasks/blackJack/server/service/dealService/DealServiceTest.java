package com.testtasks.blackJack.server.service.dealService;

import com.testtasks.blackJack.server.common.PlayerType;
import com.testtasks.blackJack.server.common.dao.DaoGameInfoI;
import com.testtasks.blackJack.server.common.dao.HandInfoI;
import com.testtasks.blackJack.server.component.CardGenerator;
import com.testtasks.blackJack.server.component.GameInfoBuilder;
import com.testtasks.blackJack.server.dao.DaoException;
import com.testtasks.blackJack.server.dao.GameDAOI;
import com.testtasks.blackJack.server.dao.SystemDaoException;
import com.testtasks.blackJack.server.dao.object.CardInfo;
import com.testtasks.blackJack.server.dao.object.HandInfo;
import com.testtasks.blackJack.server.domain.account.AccountI;
import com.testtasks.blackJack.server.domain.card.Card;
import com.testtasks.blackJack.server.domain.deal.DealInfoI;
import com.testtasks.blackJack.server.domain.gameInfo.GameInfoI;
import com.testtasks.blackJack.server.exception.NotEnoughMoneyException;
import com.testtasks.blackJack.server.exception.ServiceException;
import com.testtasks.blackJack.server.validator.ParamValidationException;
import com.testtasks.blackJack.server.validator.SumRequestValidator;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class DealServiceTest {

    private DealService serviceForTests = new DealService();

    private GameDAOI gameDAOMock;
    private CardGenerator cardGeneratorMock;
    private GameInfoBuilder gameInfoBuilderMock;
    private SumRequestValidator sumRequestValidatorMock;

    private DealInfoI dealInfoMock;
    private AccountI accountMock;
    private DaoGameInfoI daoGameInfoMock;
    private GameInfoI gameInfoMock;

    private CardInfo[] cardsArray;

    private static final Long DEFAULT_ACCOUNT_ID = 123456L;
    private static final Long DEFAULT_GAME_ID = 12398L;
    private static final BigDecimal DEFAULT_SUM = BigDecimal.TEN;

    private static final Card FIRST_PLAYER_CARD = Card.CRUSADE_ACE;
    private static final Card SECOND_PLAYER_CARD = Card.CRUSADE_FIVE;
    private static final Card FIRST_DEALER_CARD = Card.CRUSADE_THREE;
    private static final Card SECOND_DEALER_CARD = Card.CRUSADE_TEN;

    @Before
    public void initData() throws ServiceException {
        gameDAOMock = mock(GameDAOI.class);
        cardGeneratorMock = mock(CardGenerator.class);
        gameInfoBuilderMock = mock(GameInfoBuilder.class);
        sumRequestValidatorMock = mock(SumRequestValidator.class);

        dealInfoMock = mock(DealInfoI.class);
        accountMock = mock(AccountI.class);
        daoGameInfoMock = mock(DaoGameInfoI.class);
        gameInfoMock = mock(GameInfoI.class);

        cardsArray = new CardInfo[]{new CardInfo(FIRST_PLAYER_CARD, PlayerType.PLAYER),
                new CardInfo(SECOND_PLAYER_CARD, PlayerType.PLAYER),
                new CardInfo(FIRST_DEALER_CARD, PlayerType.DEALER),
                new CardInfo(SECOND_DEALER_CARD, PlayerType.DEALER),};

        serviceForTests.setCardGenerator(cardGeneratorMock);
        serviceForTests.setGameInfoBuilder(gameInfoBuilderMock);
        serviceForTests.setGameDAO(gameDAOMock);
        serviceForTests.setSumRequestValidator(sumRequestValidatorMock);

        when(gameDAOMock.getAccount(DEFAULT_ACCOUNT_ID)).thenReturn(accountMock);

        when(dealInfoMock.getDealSum()).thenReturn(DEFAULT_SUM);

        when(accountMock.getAccountSum()).thenReturn(DEFAULT_SUM);
        when(accountMock.getAccountId()).thenReturn(DEFAULT_ACCOUNT_ID);

        when(gameDAOMock.createGame(DEFAULT_ACCOUNT_ID, DEFAULT_SUM)).thenReturn(daoGameInfoMock);
        when(daoGameInfoMock.getGameId()).thenReturn(DEFAULT_GAME_ID);

        when(cardGeneratorMock.generate(any(HandInfo.class))).thenReturn(FIRST_PLAYER_CARD, SECOND_PLAYER_CARD,
                FIRST_DEALER_CARD, SECOND_DEALER_CARD);

        when(gameInfoBuilderMock.build(eq(daoGameInfoMock), any(HandInfoI.class))).thenReturn(gameInfoMock);
        when(gameInfoMock.getBet()).thenReturn(DEFAULT_SUM);
    }

    @Test
    public void testSuccess() throws ServiceException {
        assertEquals(gameInfoMock, serviceForTests.startDeal(DEFAULT_ACCOUNT_ID, dealInfoMock));

        verify(sumRequestValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_SUM);
        verify(sumRequestValidatorMock).validate(anyLong(), any(BigDecimal.class));

        verify(gameDAOMock).getAccount(DEFAULT_ACCOUNT_ID);
        verify(gameDAOMock).getAccount(anyLong());

        verify(accountMock).getAccountSum();
        verify(dealInfoMock, atLeastOnce()).getDealSum();

        verify(gameDAOMock).createGame(DEFAULT_ACCOUNT_ID, DEFAULT_SUM);
        verify(gameDAOMock).createGame(anyLong(), any(BigDecimal.class));

        verify(cardGeneratorMock, times(4)).generate(any(HandInfo.class));

        verify(gameDAOMock).saveCardsInfo(DEFAULT_GAME_ID, cardsArray);

        verify(gameDAOMock).replenishPurse(DEFAULT_ACCOUNT_ID, DEFAULT_SUM.negate());
    }

    @Test
    public void testWhenSumRequestValidatorThrowParamValidationException() throws ServiceException {
        ParamValidationException exceptionForTest = new ParamValidationException("");
        doThrow(exceptionForTest).when(sumRequestValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_SUM);

        try {
            serviceForTests.startDeal(DEFAULT_ACCOUNT_ID, dealInfoMock);
            fail();
        } catch (ParamValidationException ex){
            assertEquals(exceptionForTest, ex);

            verify(sumRequestValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_SUM);
            verify(sumRequestValidatorMock).validate(anyLong(), any(BigDecimal.class));

            verify(gameDAOMock, never()).getAccount(DEFAULT_ACCOUNT_ID);
            verify(gameDAOMock, never()).getAccount(anyLong());

            verify(accountMock, never()).getAccountSum();
            verify(dealInfoMock, times(1)).getDealSum();

            verify(gameDAOMock, never()).createGame(DEFAULT_ACCOUNT_ID, DEFAULT_SUM);
            verify(gameDAOMock, never()).createGame(anyLong(), any(BigDecimal.class));

            verify(cardGeneratorMock, never()).generate(any(HandInfo.class));

            verify(gameDAOMock, never()).saveCardsInfo(DEFAULT_GAME_ID, cardsArray);

            verify(gameDAOMock, never()).replenishPurse(DEFAULT_ACCOUNT_ID, DEFAULT_SUM.negate());
        }
    }

    @Test
    public void testWhenGetAccountThrowDaoException() throws ServiceException {
        DaoException exceptionForTest = new SystemDaoException();
        doThrow(exceptionForTest).when(gameDAOMock).getAccount(DEFAULT_ACCOUNT_ID);

        try {
            serviceForTests.startDeal(DEFAULT_ACCOUNT_ID, dealInfoMock);
            fail();
        } catch (SystemDaoException ex){
            assertEquals(exceptionForTest, ex);

            verify(sumRequestValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_SUM);
            verify(sumRequestValidatorMock).validate(anyLong(), any(BigDecimal.class));

            verify(gameDAOMock).getAccount(DEFAULT_ACCOUNT_ID);
            verify(gameDAOMock).getAccount(anyLong());

            verify(accountMock, never()).getAccountSum();
            verify(dealInfoMock, times(1)).getDealSum();

            verify(gameDAOMock, never()).createGame(DEFAULT_ACCOUNT_ID, DEFAULT_SUM);
            verify(gameDAOMock, never()).createGame(anyLong(), any(BigDecimal.class));

            verify(cardGeneratorMock, never()).generate(any(HandInfo.class));

            verify(gameDAOMock, never()).saveCardsInfo(DEFAULT_GAME_ID, cardsArray);

            verify(gameDAOMock, never()).replenishPurse(DEFAULT_ACCOUNT_ID, DEFAULT_SUM.negate());
        }
    }

    @Test
    public void testWhenCreateGameThrowDaoException() throws ServiceException {
        DaoException exceptionForTest = new SystemDaoException();
        doThrow(exceptionForTest).when(gameDAOMock).createGame(DEFAULT_ACCOUNT_ID, DEFAULT_SUM);

        try {
            serviceForTests.startDeal(DEFAULT_ACCOUNT_ID, dealInfoMock);
            fail();
        } catch (SystemDaoException ex){
            assertEquals(exceptionForTest, ex);

            verify(sumRequestValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_SUM);
            verify(sumRequestValidatorMock).validate(anyLong(), any(BigDecimal.class));

            verify(gameDAOMock).getAccount(DEFAULT_ACCOUNT_ID);
            verify(gameDAOMock).getAccount(anyLong());

            verify(accountMock).getAccountSum();
            verify(dealInfoMock, atLeastOnce()).getDealSum();

            verify(gameDAOMock).createGame(DEFAULT_ACCOUNT_ID, DEFAULT_SUM);
            verify(gameDAOMock).createGame(anyLong(), any(BigDecimal.class));

            verify(cardGeneratorMock, never()).generate(any(HandInfo.class));

            verify(gameDAOMock, never()).saveCardsInfo(DEFAULT_GAME_ID, cardsArray);

            verify(gameDAOMock, never()).replenishPurse(DEFAULT_ACCOUNT_ID, DEFAULT_SUM.negate());
        }
    }

    @Test
    public void testWhenSaveCardsInfoThrowDaoException() throws ServiceException {
        DaoException exceptionForTest = new SystemDaoException();
        doThrow(exceptionForTest).when(gameDAOMock).saveCardsInfo(DEFAULT_GAME_ID, cardsArray);

        try {
            serviceForTests.startDeal(DEFAULT_ACCOUNT_ID, dealInfoMock);
            fail();
        } catch (SystemDaoException ex){
            assertEquals(exceptionForTest, ex);

            verify(sumRequestValidatorMock).validate(DEFAULT_ACCOUNT_ID, DEFAULT_SUM);
            verify(sumRequestValidatorMock).validate(anyLong(), any(BigDecimal.class));

            verify(gameDAOMock).getAccount(DEFAULT_ACCOUNT_ID);
            verify(gameDAOMock).getAccount(anyLong());

            verify(accountMock).getAccountSum();
            verify(dealInfoMock, atLeastOnce()).getDealSum();

            verify(gameDAOMock).createGame(DEFAULT_ACCOUNT_ID, DEFAULT_SUM);
            verify(gameDAOMock).createGame(anyLong(), any(BigDecimal.class));

            verify(cardGeneratorMock, times(4)).generate(any(HandInfo.class));

            verify(gameDAOMock).saveCardsInfo(DEFAULT_GAME_ID, cardsArray);

            verify(gameDAOMock, never()).replenishPurse(DEFAULT_ACCOUNT_ID, DEFAULT_SUM.negate());
        }
    }

    @Test
    public void testWhenDealSumBiggerThanAccountSum() throws ServiceException {
        BigDecimal dealBet = BigDecimal.valueOf(100);
        when(dealInfoMock.getDealSum()).thenReturn(dealBet);
        try {
            serviceForTests.startDeal(DEFAULT_ACCOUNT_ID, dealInfoMock);
            fail();
        } catch (NotEnoughMoneyException ex){
            assertEquals("Не достаточно средств для начала игры!", ex.getMessage());

            verify(sumRequestValidatorMock).validate(DEFAULT_ACCOUNT_ID, dealBet);
            verify(sumRequestValidatorMock).validate(anyLong(), any(BigDecimal.class));

            verify(gameDAOMock).getAccount(DEFAULT_ACCOUNT_ID);
            verify(gameDAOMock).getAccount(anyLong());

            verify(accountMock).getAccountSum();
            verify(dealInfoMock, atLeastOnce()).getDealSum();

            verify(gameDAOMock, never()).createGame(DEFAULT_ACCOUNT_ID, DEFAULT_SUM);
            verify(gameDAOMock, never()).createGame(anyLong(), any(BigDecimal.class));

            verify(cardGeneratorMock, never()).generate(any(HandInfo.class));

            verify(gameDAOMock, never()).saveCardsInfo(DEFAULT_GAME_ID, cardsArray);

            verify(gameDAOMock, never()).replenishPurse(DEFAULT_ACCOUNT_ID, DEFAULT_SUM.negate());
        }
    }
}
