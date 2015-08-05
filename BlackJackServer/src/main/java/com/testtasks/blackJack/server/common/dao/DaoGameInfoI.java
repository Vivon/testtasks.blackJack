package com.testtasks.blackJack.server.common.dao;

import com.testtasks.blackJack.server.common.GameState;

import java.math.BigDecimal;

/**
 * Данные о игре полученные из БД
 */
public interface DaoGameInfoI {

    /**
     * Ид игры
     */
    Long getGameId();

    /**
     * Ставка игры
     */
    BigDecimal getBet();

    /**
     * Статус игры
     */
    GameState getState();
}
