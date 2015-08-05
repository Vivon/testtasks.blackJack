package com.testtasks.blackJack.server.dao.executor;

import com.testtasks.blackJack.server.dao.mapper.GameInfoMapper;
import com.testtasks.blackJack.server.dao.object.DaoGameInfo;
import org.springframework.jdbc.core.RowMapper;

public class GetGameInfoExecutor extends SelectExecutorAbs<DaoGameInfo>{

    private Long gameId;
    private Long accountId;

    public GetGameInfoExecutor(Long gameId, Long accountId) {
        this.gameId = gameId;
        this.accountId = accountId;
    }

    @Override
    protected String getSQL() {
        return "SELECT game.game_id, game.bet, game.state FROM game WHERE game.game_id = ? AND game.account_id = ?";
    }

    @Override
    protected Object[] getArguments() {
        return new Object[]{gameId, accountId};
    }

    @Override
    protected RowMapper<DaoGameInfo> getObjectMapper() {
        return new GameInfoMapper();
    }
}
