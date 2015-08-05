package com.testtasks.blackJack.server.dao.mapper;

import com.testtasks.blackJack.server.domain.account.Account;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper implements RowMapper<Account> {

    static final String ACCOUNT_ID_ROW_ALIAS = "account_id";
    static final String ACCOUNT_SUM_ROW_ALIAS = "account_sum";

    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        Account account = new Account();
        account.setAccountId(rs.getLong(ACCOUNT_ID_ROW_ALIAS));
        account.setAccountSum(rs.getBigDecimal(ACCOUNT_SUM_ROW_ALIAS));
        return account;
    }
}
