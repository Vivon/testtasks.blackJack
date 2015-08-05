package com.testtasks.blackJack.server.dao;

import com.testtasks.blackJack.server.common.GameState;
import com.testtasks.blackJack.server.common.dao.DaoGameInfoI;
import com.testtasks.blackJack.server.common.dao.HandInfoI;
import com.testtasks.blackJack.server.dao.object.CardInfo;
import com.testtasks.blackJack.server.domain.account.AccountI;

import java.math.BigDecimal;

public interface GameDAOI {

    /**
     * Получить данные о счете
     * @param accountId ид счета
     * @throws DaoException
     */
    AccountI getAccount(Long accountId) throws DaoException;

    /**
     * Получить данные о игре
     * @param gameId ид игры
     * @param accountId ид счета
     * @throws DaoException
     */
    DaoGameInfoI getGameInfo(Long gameId, Long accountId) throws DaoException;

    /**
     * Получить данные о картах использованных в игре
     * @param gameId ид игры
     * @throws DaoException
     */
    HandInfoI getHandInfo(Long gameId) throws DaoException;

    /**
     * Пополнить счет
     * @param accountId ид счета
     * @param sumToReplenish сумма для пополнения
     * @throws DaoException
     */
    AccountI replenishPurse(Long accountId, BigDecimal sumToReplenish) throws DaoException;

    /**
     * Изменить состояние игры
     * @param gameId ид игры
     * @param gameState новое состояние
     * @throws DaoException
     */
    void updateGameState(Long gameId, GameState gameState) throws DaoException;

    /**
     * Создать новый счет
     * @throws DaoException
     */
    AccountI createAccount() throws DaoException;

    /**
     * Создать новую игру
     * @param accountId ид счета
     * @param dealSum сумма ставки
     * @throws DaoException
     */
    DaoGameInfoI createGame(Long accountId, BigDecimal dealSum) throws DaoException;

    /**
     * Сохранить вытянутую карту в игре
     * @param gameId ид игры
     * @param cards вытянутые карты
     * @throws DaoException
     */
    void saveCardsInfo(Long gameId, CardInfo... cards) throws DaoException;
}
