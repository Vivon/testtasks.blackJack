package com.testtasks.blackJack.server.common.utils;

import com.testtasks.blackJack.server.common.dao.HandInfoI;
import com.testtasks.blackJack.server.component.CardGenerator;
import com.testtasks.blackJack.server.dao.object.CardInfo;
import com.testtasks.blackJack.server.dao.object.HandInfo;
import com.testtasks.blackJack.server.common.PlayerType;
import com.testtasks.blackJack.server.domain.card.Card;
import com.testtasks.blackJack.server.domain.player.PlayerHandI;

import java.util.ArrayList;
import java.util.List;

public class HandUtils {

    public static void expandedHandsToPlayers(PlayerHandI player, PlayerHandI dealer, HandInfoI handInfo) {
        for (CardInfo cardInfo : handInfo.getCards()) {
            if (cardInfo.getPlayerType() == PlayerType.PLAYER) {
                player.addCards(cardInfo.getCard());
            } else {
                dealer.addCards(cardInfo.getCard());
            }
        }
    }

    public static HandInfo generateBeginHandInfo(CardGenerator cardGenerator){
        HandInfo handInfo = new HandInfo();
        handInfo.addCard(generateCardInfo(handInfo, PlayerType.PLAYER, cardGenerator));
        handInfo.addCard(generateCardInfo(handInfo, PlayerType.PLAYER, cardGenerator));
        handInfo.addCard(generateCardInfo(handInfo, PlayerType.DEALER, cardGenerator));
        handInfo.addCard(generateCardInfo(handInfo, PlayerType.DEALER, cardGenerator));
        return handInfo;
    }

    private static CardInfo generateCardInfo(HandInfoI handInfo, PlayerType playerType, CardGenerator cardGenerator){
        Card newCard = cardGenerator.generate(handInfo);
        return new CardInfo(newCard, playerType);
    }

    public static List<Card> getCardsByPlayerType(HandInfoI hand, PlayerType playerType){
        List<Card> cards = new ArrayList<Card>();
        for (CardInfo cardInfo : hand.getCards()) {
            if (cardInfo.getPlayerType().equals(playerType)){
                cards.add(cardInfo.getCard());
            }
        }
        return cards;
    }
}
