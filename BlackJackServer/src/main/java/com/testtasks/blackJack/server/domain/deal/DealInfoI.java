package com.testtasks.blackJack.server.domain.deal;

import java.math.BigDecimal;

/**
 * Данные о начале игры
 */
public interface DealInfoI {

    /**
     * Сумма ставки
     */
    BigDecimal getDealSum();
}
