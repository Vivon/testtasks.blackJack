package com.testtasks.blackJack.server.domain.player;

import com.testtasks.blackJack.server.component.GameLogic;
import com.testtasks.blackJack.server.domain.card.Card;

import java.util.ArrayList;
import java.util.List;

public class PlayerHand implements PlayerHandI {

    private List<Card> hand;
    private Integer score;

    public PlayerHand() {
        this.hand = new ArrayList<Card>();
        this.score = 0;
    }

    @Override
    public void addCards(Card... cards){
        for (Card card : cards) {
            hand.add(card);
        }
    }

    @Override
    public List<Card> getHand() {
        return hand;
    }

    @Override
    public Integer getScore() {
        return score;
    }

    @Override
    public void recalcScore(){
        this.score = GameLogic.calcScore(hand);
    }
}
