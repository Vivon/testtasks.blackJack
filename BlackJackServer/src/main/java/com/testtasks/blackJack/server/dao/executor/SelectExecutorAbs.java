package com.testtasks.blackJack.server.dao.executor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public abstract class SelectExecutorAbs<T> implements ExecutorI<T> {

    @Override
    public T execute(JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.queryForObject(getSQL(), getArguments(), getObjectMapper());
    }

    protected abstract String getSQL();

    protected abstract Object[] getArguments();

    protected abstract RowMapper<T> getObjectMapper();
}
