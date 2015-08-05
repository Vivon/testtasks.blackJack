package com.testtasks.blackJack.server.dao.object;

import com.testtasks.blackJack.server.common.GameState;
import com.testtasks.blackJack.server.common.dao.DaoGameInfoI;

import java.math.BigDecimal;

public class DaoGameInfo implements DaoGameInfoI {

    private Long gameId;
    private BigDecimal bet;
    private GameState state;

    public DaoGameInfo(Long gameId, BigDecimal bet, GameState state) {
        this.gameId = gameId;
        this.bet = bet;
        this.state = state;
    }

    @Override
    public Long getGameId() {
        return gameId;
    }

    @Override
    public BigDecimal getBet() {
        return bet;
    }

    @Override
    public GameState getState() {
        return state;
    }
}
