package com.testtasks.blackJack.server.component;

import com.testtasks.blackJack.server.common.Consts;
import com.testtasks.blackJack.server.common.WinType;
import com.testtasks.blackJack.server.domain.card.Card;
import com.testtasks.blackJack.server.domain.player.PlayerHandI;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameLogicTest {

    private GameLogic gameLogicForTests = new GameLogic();

    private static final Integer DEFAULT_DEALER_SCORE = 16;
    private static final Integer DEFAULT_BIGGER_SCORE = 18;
    private static final Integer DEFAULT_SCORE = 19;
    private static final Integer BIGGER_SCORE = 23;

    private PlayerHandI playerMock;
    private PlayerHandI dealerMock;

    @Before
    public void initData(){
        playerMock = mock(PlayerHandI.class);
        dealerMock = mock(PlayerHandI.class);
    }

    @Test
    public void testIsPlayerHasBlackJack(){
        when(playerMock.getScore()).thenReturn(DEFAULT_SCORE)
                .thenReturn(Consts.BLACK_JACK_SCORE)
                .thenReturn(BIGGER_SCORE);
        assertFalse(gameLogicForTests.isPlayerHasBlackJack(playerMock));
        assertTrue(gameLogicForTests.isPlayerHasBlackJack(playerMock));
        assertFalse(gameLogicForTests.isPlayerHasBlackJack(playerMock));
    }

    @Test
    public void testIsPlayerNotHaveBust(){
        when(playerMock.getScore()).thenReturn(DEFAULT_SCORE)
                .thenReturn(Consts.BLACK_JACK_SCORE)
                .thenReturn(BIGGER_SCORE);
        assertTrue(gameLogicForTests.isPlayerNotHaveBust(playerMock));
        assertTrue(gameLogicForTests.isPlayerNotHaveBust(playerMock));
        assertFalse(gameLogicForTests.isPlayerNotHaveBust(playerMock));
    }

    @Test
    public void testIsDealerNotHaveMaxScore(){
        when(dealerMock.getScore()).thenReturn(DEFAULT_DEALER_SCORE)
                .thenReturn(Consts.DEALER_MAX_SCORE)
                .thenReturn(DEFAULT_BIGGER_SCORE);
        assertTrue(gameLogicForTests.isDealerNotHaveMaxScore(dealerMock));
        assertTrue(gameLogicForTests.isDealerNotHaveMaxScore(dealerMock));
        assertFalse(gameLogicForTests.isDealerNotHaveMaxScore(dealerMock));
    }

    @Test
    public void testIsPlayersHaveEqualScore(){
        when(playerMock.getScore()).thenReturn(DEFAULT_SCORE)
                .thenReturn(Consts.BLACK_JACK_SCORE);
        when(dealerMock.getScore()).thenReturn(DEFAULT_DEALER_SCORE)
                .thenReturn(Consts.BLACK_JACK_SCORE);

        assertFalse(gameLogicForTests.isPlayersHaveEqualScore(playerMock, dealerMock));
        assertTrue(gameLogicForTests.isPlayersHaveEqualScore(playerMock, dealerMock));
    }

    @Test
    public void testIsPlayerHaveBiggerScore(){
        when(playerMock.getScore()).thenReturn(DEFAULT_SCORE)
                .thenReturn(Consts.BLACK_JACK_SCORE).thenReturn(DEFAULT_SCORE);
        when(dealerMock.getScore()).thenReturn(DEFAULT_DEALER_SCORE)
                .thenReturn(Consts.BLACK_JACK_SCORE).thenReturn(BIGGER_SCORE);

        assertTrue(gameLogicForTests.isPlayerHaveBiggerScore(playerMock, dealerMock));
        assertFalse(gameLogicForTests.isPlayerHaveBiggerScore(playerMock, dealerMock));
        assertFalse(gameLogicForTests.isPlayerHaveBiggerScore(playerMock, dealerMock));
    }

    @Test
    public void testGetWinnerMethodWinTypePUSH(){
        when(playerMock.getScore()).thenReturn(Consts.BLACK_JACK_SCORE);
        when(dealerMock.getScore()).thenReturn(Consts.BLACK_JACK_SCORE);

        assertEquals(WinType.PUSH, gameLogicForTests.getWinner(playerMock, dealerMock));

    }

    @Test
    public void testGetWinnerMethodWinTypePlayerWin(){
        when(playerMock.getScore()).thenReturn(Consts.BLACK_JACK_SCORE);
        when(dealerMock.getScore()).thenReturn(DEFAULT_DEALER_SCORE);

        assertEquals(WinType.PLAYER_WIN, gameLogicForTests.getWinner(playerMock, dealerMock));
    }

    @Test
    public void testGetWinnerMethodWinTypePlayerWin2(){
        when(playerMock.getScore()).thenReturn(DEFAULT_SCORE);
        when(dealerMock.getScore()).thenReturn(DEFAULT_DEALER_SCORE);

        assertEquals(WinType.PLAYER_WIN, gameLogicForTests.getWinner(playerMock, dealerMock));
    }

    @Test
    public void testGetWinnerMethodWinTypeDealerWin(){
        when(playerMock.getScore()).thenReturn(DEFAULT_DEALER_SCORE);
        when(dealerMock.getScore()).thenReturn(DEFAULT_SCORE);

        assertEquals(WinType.DEALER_WIN, gameLogicForTests.getWinner(playerMock, dealerMock));
    }

    @Test
    public void testCalcScoreMethodWithoutAce(){
        List<Card> hand = new ArrayList<Card>();
        hand.add(Card.CRUSADE_KING);
        hand.add(Card.CRUSADE_FIVE);

        assertEquals(15, gameLogicForTests.calcScore(hand).intValue());
    }

    @Test
    public void testCalcScoreMethodWithAce(){
        List<Card> hand = new ArrayList<Card>();
        hand.add(Card.CRUSADE_ACE);
        hand.add(Card.CRUSADE_FIVE);

        assertEquals(16, gameLogicForTests.calcScore(hand).intValue());
    }

    @Test
    public void testCalcScoreMethodWithAceWhenScoreBiggerThenBlackJack(){
        List<Card> hand = new ArrayList<Card>();
        hand.add(Card.CRUSADE_ACE);
        hand.add(Card.CRUSADE_FIVE);
        hand.add(Card.HEARTS_KING);

        assertEquals(16, gameLogicForTests.calcScore(hand).intValue());
    }

    @Test
    public void testIsDealerNotHaveMaxScoreMethod(){
        List<Card> hand = new ArrayList<Card>();
        hand.add(Card.CRUSADE_FIVE);
        hand.add(Card.HEARTS_KING);
        assertTrue(gameLogicForTests.isDealerNotHaveMaxScore(hand));

        hand.add(Card.DIAMONDS_JACK);
        assertFalse(gameLogicForTests.isDealerNotHaveMaxScore(hand));
    }

    @Test
    public void testIsDealerNotHaveMaxScoreMethodWithListCard(){
        List<Card> hand = new ArrayList<Card>();
        hand.add(Card.CRUSADE_EIGHT);
        hand.add(Card.CRUSADE_SIX);
        assertTrue(gameLogicForTests.isDealerNotHaveMaxScore(hand));

        hand.add(Card.DIAMONDS_TWO);
        assertTrue(gameLogicForTests.isDealerNotHaveMaxScore(hand));

        hand.add(Card.HEARTS_TWO);
        assertFalse(gameLogicForTests.isDealerNotHaveMaxScore(hand));
    }

    @Test
    public void testIsPlayerNotHaveBustMethodWithListCard(){
        List<Card> hand = new ArrayList<Card>();
        hand.add(Card.CRUSADE_EIGHT);
        hand.add(Card.CRUSADE_JACK);
        assertTrue(gameLogicForTests.isPlayerNotHaveBust(hand));

        hand.add(Card.DIAMONDS_TWO);
        assertTrue(gameLogicForTests.isPlayerNotHaveBust(hand));

        hand.add(Card.HEARTS_TWO);
        assertFalse(gameLogicForTests.isPlayerNotHaveBust(hand));
    }
}
