package com.testtasks.blackJack.server.dao.executor;

import com.testtasks.blackJack.server.dao.object.CardInfo;

import java.util.HashMap;
import java.util.Map;

public class SaveCardExecutor extends BatchInsertExecutorAbs{

    private static final String TABLE_NAME = "hand";
    private static final String KEY_COLUMN_COLUMN_NAME = "id";
    private static final String GAME_ID_COLUMN_NAME = "game_id";
    private static final String ACCOUNT_ID_COLUMN_NAME = "account_id";
    private static final String PLAYER_COLUMN_NAME = "player";
    private static final String CARD_ID_COLUMN_NAME = "card_id";

    private Long gameId;
    private HashMap<String, Object>[] cardsBath;

    public SaveCardExecutor(Long gameId, CardInfo... cards) {
        this.gameId = gameId;
        cardsBath = new HashMap[cards.length];
        int i = 0;
        for (CardInfo cardInfo : cards) {
            HashMap<String, Object> cardMapper = new HashMap<String, Object>();
            cardMapper.put("game_id", gameId);
            cardMapper.put("player", cardInfo.getPlayerType().name());
            cardMapper.put("card_id", cardInfo.getCard().getCardId());
            cardsBath[i] = cardMapper;
            i++;
        }
    }

    @Override
    protected Map<String, Object>[] getArguments() {
        return cardsBath;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getKeyColumnName() {
        return KEY_COLUMN_COLUMN_NAME;
    }

    @Override
    protected String[] getColumnsName() {
        return new String[]{GAME_ID_COLUMN_NAME, ACCOUNT_ID_COLUMN_NAME, PLAYER_COLUMN_NAME, CARD_ID_COLUMN_NAME};
    }
}
