package com.testtasks.blackJack.server.common.dao;

import com.testtasks.blackJack.server.common.PlayerType;
import com.testtasks.blackJack.server.domain.card.Card;

/**
 * Информация о карте в руке
 */
public interface CardInfoI {

    /**
     * Получить карту игрока
     */
    Card getCard();

    /**
     * Получить тип игрока(пользователь/диллер)
     */
    PlayerType getPlayerType();
}
