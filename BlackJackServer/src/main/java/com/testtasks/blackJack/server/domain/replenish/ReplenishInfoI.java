package com.testtasks.blackJack.server.domain.replenish;

import java.math.BigDecimal;

/**
 * Данные для пополнения суммы счета
 */
public interface ReplenishInfoI {

    /**
     * Сумма для пополнения
     */
    BigDecimal getSumToReplenish();

}
