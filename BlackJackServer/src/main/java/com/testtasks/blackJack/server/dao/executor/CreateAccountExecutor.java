package com.testtasks.blackJack.server.dao.executor;

import com.testtasks.blackJack.server.common.Consts;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountExecutor extends InsertExecutorAbs {

    private static final String TABLE_NAME = "account";
    private static final String KEY_COLUMN_NAME = "account_id";
    private static final String ACCOUNT_SUM_COLUMN_NAME = "account_sum";

    public CreateAccountExecutor() {
    }

    @Override
    protected Map<String, Object> getArguments() {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put(ACCOUNT_SUM_COLUMN_NAME, Consts.DEFAULT_ACCOUNT_SUM);
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
        return new String[]{ACCOUNT_SUM_COLUMN_NAME};
    }
}
