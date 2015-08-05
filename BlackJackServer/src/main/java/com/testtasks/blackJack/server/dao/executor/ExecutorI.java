package com.testtasks.blackJack.server.dao.executor;

import org.springframework.jdbc.core.JdbcTemplate;

public interface ExecutorI<T> {

    T execute(JdbcTemplate jdbcTemplate);

}
