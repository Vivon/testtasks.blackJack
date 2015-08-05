package com.testtasks.blackJack.server.domain.gameInfo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.testtasks.blackJack.server.common.GameState;
import com.testtasks.blackJack.server.domain.player.PlayerHand;

import java.math.BigDecimal;

/**
 * Информация о текущем состоянии игры
 */
public interface GameInfoI {

    /**
     * Ид игры
     */
    @JsonProperty("gameId")
    Long getGameId();

    /**
     * Состояние игры
     */
    @JsonProperty("state")
    GameState getState();

    /**
     * Ставка игры
     */
    @JsonProperty("bet")
    BigDecimal getBet();

    /**
     * Успешная операция
     */
    @JsonProperty("success")
    Boolean isSuccess();

    /**
     * Данные о руке пользователя
     */
    @JsonProperty("player")
    PlayerHand getPlayer();

    /**
     * Данные о руке Диллера
     * @return
     */
    @JsonProperty("dealer")
    PlayerHand getDealer();

    /**
     * Пересчитать счет карт
     */
    @JsonIgnore
    void recalcScores();
}
