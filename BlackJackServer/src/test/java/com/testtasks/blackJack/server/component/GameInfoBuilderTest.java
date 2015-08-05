package com.testtasks.blackJack.server.component;

import com.testtasks.blackJack.server.common.GameState;
import com.testtasks.blackJack.server.common.PlayerType;
import com.testtasks.blackJack.server.common.dao.DaoGameInfoI;
import com.testtasks.blackJack.server.common.dao.HandInfoI;
import com.testtasks.blackJack.server.dao.object.CardInfo;
import com.testtasks.blackJack.server.domain.card.Card;
import com.testtasks.blackJack.server.domain.gameInfo.GameInfoI;
import com.testtasks.blackJack.server.domain.player.PlayerHandI;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameInfoBuilderTest {

    private DaoGameInfoI daoGameInfoMock;
    private HandInfoI handInfoMock;

    private GameInfoBuilder gameInfoBuilder = new GameInfoBuilder();

    private static final Long DEFAULT_GAME_ID = 1234L;
    private static final BigDecimal DEFAULT_BET = BigDecimal.TEN;
    private static final GameState DEFAULT_GAME_STATE = GameState.D;

    private static final Card PLAYER_FIRST_CARD = Card.CRUSADE_ACE;
    private static final Card PLAYER_SECOND_CARD = Card.SPADES_SIX;

    private static final Card DEALER_FIRST_CARD = Card.HEARTS_FIVE;
    private static final Card DEALER_SECOND_CARD = Card.DIAMONDS_NINE;


    @Before
    public void initDataForTests(){
        daoGameInfoMock = mock(DaoGameInfoI.class);
        handInfoMock = mock(HandInfoI.class);

        when(daoGameInfoMock.getGameId()).thenReturn(DEFAULT_GAME_ID);
        when(daoGameInfoMock.getBet()).thenReturn(DEFAULT_BET);
        when(daoGameInfoMock.getState()).thenReturn(DEFAULT_GAME_STATE);

        when(handInfoMock.getCards()).thenReturn(new ArrayList<CardInfo>(){{
                                                    add(new CardInfo(PLAYER_FIRST_CARD, PlayerType.PLAYER));
                                                    add(new CardInfo(PLAYER_SECOND_CARD, PlayerType.PLAYER));

                                                    add(new CardInfo(DEALER_FIRST_CARD, PlayerType.DEALER));
                                                    add(new CardInfo(DEALER_SECOND_CARD, PlayerType.DEALER));
                                                }});
    }

    @Test
    public void testBuildGameInfo(){
        GameInfoI result = gameInfoBuilder.build(daoGameInfoMock, handInfoMock);
        assertEquals(DEFAULT_GAME_ID, result.getGameId());
        assertEquals(DEFAULT_BET, result.getBet());
        assertEquals(DEFAULT_GAME_STATE, result.getState());
        PlayerHandI player = result.getPlayer();
        assertEquals(2, player.getHand().size());
        assertEquals(17, player.getScore().intValue());
        assertTrue(player.getHand().contains(PLAYER_FIRST_CARD));
        assertTrue(player.getHand().contains(PLAYER_SECOND_CARD));

        PlayerHandI dealer = result.getDealer();
        assertEquals(2, dealer.getHand().size());
        assertEquals(14, dealer.getScore().intValue());
        assertTrue(dealer.getHand().contains(DEALER_FIRST_CARD));
        assertTrue(dealer.getHand().contains(DEALER_SECOND_CARD));

    }
}
