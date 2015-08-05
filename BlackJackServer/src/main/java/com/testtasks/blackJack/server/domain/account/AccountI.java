package com.testtasks.blackJack.server.domain.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Данные о счете
 */
public interface AccountI {

    /**
     * Получить номер счета
     */
    @JsonProperty(value = "accountId", required = true)
    Long getAccountId();

    /**
     * Сумма счета
     */
    @JsonProperty(value = "accountSum", required = true)
    BigDecimal getAccountSum();
}
