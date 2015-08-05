package com.testtasks.blackJack.server.domain.deal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.testtasks.blackJack.server.common.RequestAlias;

import java.math.BigDecimal;

public class DealInfo implements DealInfoI {

    @JsonProperty(value = RequestAlias.DEAL_SUM_ALIAS, required = false)
    private BigDecimal dealSum;

    public DealInfo() {
    }

    public DealInfo(BigDecimal dealSum) {
        this.dealSum = dealSum;
    }

    @Override
    public BigDecimal getDealSum() {
        return dealSum;
    }
}
