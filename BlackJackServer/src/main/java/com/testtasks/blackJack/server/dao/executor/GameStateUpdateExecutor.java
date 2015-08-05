package com.testtasks.blackJack.server.dao.executor;

import com.testtasks.blackJack.server.common.GameState;

public class GameStateUpdateExecutor extends UpdateExecutorAbs {

    private Long gameId;
    private GameState gameState;

    public GameStateUpdateExecutor(Long gameId, GameState gameState) {
        this.gameId = gameId;
        this.gameState = gameState;
    }

    @Override
    protected String getSQL() {
        return "UPDATE game SET state = ? WHERE game.game_id = ?;";
    }

    @Override
    protected Object[] getArguments() {
        return new Object[]{gameState.getStateDBAlias(), gameId};
    }
}
