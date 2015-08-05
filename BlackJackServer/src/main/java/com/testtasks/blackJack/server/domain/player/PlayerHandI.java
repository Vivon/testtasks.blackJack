package com.testtasks.blackJack.server.domain.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.testtasks.blackJack.server.domain.card.Card;

import java.util.List;

/**
 * Данные о руке игрока
 */
public interface PlayerHandI {

    /**
     * Получить список карт игрока
     */
    @JsonProperty("hand")
    List<Card> getHand();

    /**
     * Получить сумму его карт
     */
    @JsonProperty("score")
    Integer getScore();

    /**
     * Пересчитать сумму его карт
     */
    @JsonIgnore
    void recalcScore();

    /**
     * Добавить новые карты
     */
    @JsonIgnore
    void addCards(Card... cards);
}
