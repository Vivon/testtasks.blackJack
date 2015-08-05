package com.testtasks.blackJack.server.dao.executor;

import com.testtasks.blackJack.server.dao.mapper.AccountMapper;
import com.testtasks.blackJack.server.domain.account.Account;
import org.springframework.jdbc.core.RowMapper;

public class GetAccountExecutor extends SelectExecutorAbs<Account> {

    private Long accountId;

    public GetAccountExecutor(Long accountId) {
        this.accountId = accountId;
    }

    @Override
    protected String getSQL() {
        return "SELECT account_id, account_sum FROM account WHERE account_id  = ?";
    }

    @Override
    protected Object[] getArguments() {
        return new Object[]{accountId};
    }

    @Override
    protected RowMapper<Account> getObjectMapper() {
        return new AccountMapper();
    }
}
