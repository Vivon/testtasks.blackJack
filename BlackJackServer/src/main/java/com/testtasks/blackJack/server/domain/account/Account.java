package com.testtasks.blackJack.server.domain.account;

import com.testtasks.blackJack.server.common.Consts;

import java.math.BigDecimal;


public class Account implements AccountI{

    private Long accountId;
    private BigDecimal accountSum;

    public Account() {
        this.accountSum = Consts.DEFAULT_ACCOUNT_SUM;
    }

    public Account(Long accountId, BigDecimal accountSum) {
        this.accountId = accountId;
        this.accountSum = accountSum;
    }

    @Override
    public Long getAccountId() {
        return accountId;
    }

    @Override
    public BigDecimal getAccountSum() {
        return accountSum;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setAccountSum(BigDecimal accountSum) {
        this.accountSum = accountSum;
    }
}
