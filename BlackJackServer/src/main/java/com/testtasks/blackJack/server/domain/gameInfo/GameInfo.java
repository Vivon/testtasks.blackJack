package com.testtasks.blackJack.server.domain.gameInfo;

import com.testtasks.blackJack.server.common.GameState;
import com.testtasks.blackJack.server.domain.player.PlayerHand;

import java.math.BigDecimal;

public class GameInfo implements GameInfoI {

    private Long gameId;
    private BigDecimal bet;
    private Boolean success = true;
    private GameState state;
    private PlayerHand player;
    private PlayerHand dealer;

    public GameInfo(Long gameId, BigDecimal bet, GameState state) {
        this.gameId = gameId;
        this.bet = bet;
        this.state = state;

        player= new PlayerHand();
        dealer = new PlayerHand();
    }

    @Override
    public Long getGameId() {
        return gameId;
    }

    @Override
    public GameState getState() {
        return state;
    }

    @Override
    public BigDecimal getBet() {
        return bet;
    }

    @Override
    public Boolean isSuccess() {
        return success;
    }

    @Override
    public PlayerHand getPlayer() {
        return player;
    }

    @Override
    public PlayerHand getDealer() {
        return dealer;
    }

    @Override
    public void recalcScores(){
        player.recalcScore();
        dealer.recalcScore();
    }
}
