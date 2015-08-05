package com.testtasks.blackJack.server.dao.executor;

import org.springframework.jdbc.core.JdbcTemplate;

public abstract class UpdateExecutorAbs implements ExecutorI<Integer> {

    @Override
    public Integer execute(JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.update(getSQL(), getArguments());
    }

    protected abstract String getSQL();

    protected abstract Object[] getArguments();

}
