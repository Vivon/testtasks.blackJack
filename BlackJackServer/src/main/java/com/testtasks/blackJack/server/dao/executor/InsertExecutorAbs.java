package com.testtasks.blackJack.server.dao.executor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.Map;

public abstract class InsertExecutorAbs<T extends Map> implements ExecutorI<Number>{

    @Override
    public Number execute(JdbcTemplate jdbcTemplate) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(getTableName())
                .usingGeneratedKeyColumns(getKeyColumnName()).usingColumns(getColumnsName());
        return jdbcInsert.executeAndReturnKey(getArguments());
    }

    protected abstract Map<String, Object> getArguments();

    protected abstract String getTableName();

    protected abstract String getKeyColumnName();

    protected abstract String[] getColumnsName();
}
