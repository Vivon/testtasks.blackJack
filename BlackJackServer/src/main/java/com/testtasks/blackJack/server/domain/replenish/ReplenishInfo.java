package com.testtasks.blackJack.server.domain.replenish;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.testtasks.blackJack.server.common.RequestAlias;

import java.math.BigDecimal;

public class ReplenishInfo implements ReplenishInfoI{

    @JsonProperty(value = RequestAlias.SUM_TO_REPLENISH_ALIAS, required = false)
    private BigDecimal sumToReplenish;

    public ReplenishInfo() {
    }

    @Override
    public BigDecimal getSumToReplenish() {
        return sumToReplenish;
    }

}
