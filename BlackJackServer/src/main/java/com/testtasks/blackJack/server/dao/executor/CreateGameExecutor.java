package com.testtasks.blackJack.server.dao.executor;

import com.testtasks.blackJack.server.common.GameState;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CreateGameExecutor extends InsertExecutorAbs{

    private static final String BET_COLUMN_NAME = "bet";
    private static final String ACCOUNT_ID_COLUMN_NAME = "account_id";
    private static final String STATE_COLUMN_NAME = "state";
    private static final String TABLE_NAME = "game";
    private static final String KEY_COLUMN_NAME = "game_id";

    private static final Character START_GAME_STATE = GameState.D.getStateDBAlias();

    private Long accountId;
    private BigDecimal dealSum;

    public CreateGameExecutor(Long accountId, BigDecimal dealSum) {
        this.accountId = accountId;
        this.dealSum = dealSum;
    }

    @Override
    protected Map<String, Object> getArguments() {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put(BET_COLUMN_NAME, dealSum);
        args.put(ACCOUNT_ID_COLUMN_NAME, accountId);
        args.put(STATE_COLUMN_NAME, START_GAME_STATE);
        return args;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getKeyColumnName() {
        return KEY_COLUMN_NAME;
    }

    @Override
    protected String[] getColumnsName() {
        return new String[]{BET_COLUMN_NAME, ACCOUNT_ID_COLUMN_NAME, STATE_COLUMN_NAME};
    }
}
