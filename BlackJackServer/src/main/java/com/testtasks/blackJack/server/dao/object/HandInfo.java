package com.testtasks.blackJack.server.dao.object;

import com.testtasks.blackJack.server.common.dao.HandInfoI;
import com.testtasks.blackJack.server.domain.card.Card;

import java.util.ArrayList;

public class HandInfo implements HandInfoI{

    private ArrayList<CardInfo> cards;

    public HandInfo() {
        cards = new ArrayList<CardInfo>();
    }

    @Override
    public ArrayList<CardInfo> getCards() {
        return cards;
    }

    @Override
    public void addCard(CardInfo cardInfo){
        cards.add(cardInfo);
    }

    @Override
    public CardInfo[] getCardsArray() {
        return cards.toArray(new CardInfo[cards.size()]);
    }

    @Override
    public boolean containsCard(Card card){
        for (CardInfo cardInfo : cards) {
            if (cardInfo.getCard() == card){
                return true;
            }
        }
        return false;
    }
}
