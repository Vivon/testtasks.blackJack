package com.testtasks.blackJack.server.dao.executor;

import java.math.BigDecimal;

public class ReplenishPurseUpdateExecutor extends UpdateExecutorAbs{

    private Long accountId;
    private BigDecimal sumToReplenish;

    public ReplenishPurseUpdateExecutor(Long accountId, BigDecimal sumToReplenish) {
        this.accountId = accountId;
        this.sumToReplenish = sumToReplenish;
    }

    @Override
    protected String getSQL() {
        return "UPDATE account SET account_sum= account_sum + ? WHERE account_id=?;";
    }

    @Override
    protected Object[] getArguments() {
        return new Object[]{sumToReplenish, accountId};
    }
}
