package com.testtasks.blackJack.server.common.dao;

import com.testtasks.blackJack.server.dao.object.CardInfo;
import com.testtasks.blackJack.server.domain.card.Card;

import java.util.ArrayList;

public interface HandInfoI {

    ArrayList<CardInfo> getCards();

    void addCard(CardInfo cardInfo);

    CardInfo[] getCardsArray();

    boolean containsCard(Card card);
}
