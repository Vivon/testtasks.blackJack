package com.testtasks.blackJack.server.dao.executor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public abstract class SelectManyRowsExecutorAbs <T> implements ExecutorI<List<T>> {

    @Override
    public List<T> execute(JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.query(getSQL(), getArguments(), getObjectMapper());
    }

    protected abstract String getSQL();

    protected abstract Object[] getArguments();

    protected abstract RowMapper<T> getObjectMapper();
}
