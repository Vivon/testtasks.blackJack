package com.testtasks.blackJack.server.dao.object;

import com.testtasks.blackJack.server.common.dao.CardInfoI;
import com.testtasks.blackJack.server.common.PlayerType;
import com.testtasks.blackJack.server.domain.card.Card;

public class CardInfo implements CardInfoI {

    private Card card;
    private PlayerType playerType;

    public CardInfo(Card card, PlayerType playerType) {
        this.card = card;
        this.playerType = playerType;
    }

    @Override
    public Card getCard() {
        return card;
    }

    @Override
    public PlayerType getPlayerType() {
        return playerType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardInfo cardInfo = (CardInfo) o;

        if (card != cardInfo.card) return false;

        return true;
    }
}
