package com.testtasks.blackJack.server.dao.executor;

import com.testtasks.blackJack.server.dao.mapper.CardInfoMapper;
import com.testtasks.blackJack.server.dao.object.CardInfo;
import org.springframework.jdbc.core.RowMapper;

public class CardInfoExecutor extends SelectManyRowsExecutorAbs<CardInfo> {

    private Long gameId;

    public CardInfoExecutor(Long gameId) {
        this.gameId = gameId;
    }

    @Override
    protected String getSQL() {
        return "SELECT hand.player, hand.card_id FROM hand WHERE hand.game_id = ?";
    }

    @Override
    protected Object[] getArguments() {
        return new Object[]{gameId};
    }

    @Override
    protected RowMapper<CardInfo> getObjectMapper() {
        return new CardInfoMapper();
    }
}
