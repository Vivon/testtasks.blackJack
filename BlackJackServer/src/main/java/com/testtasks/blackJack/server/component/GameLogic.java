package com.testtasks.blackJack.server.component;

import com.testtasks.blackJack.server.common.Consts;
import com.testtasks.blackJack.server.common.WinType;
import com.testtasks.blackJack.server.domain.card.Card;
import com.testtasks.blackJack.server.domain.player.PlayerHandI;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameLogic {

    public boolean isPlayerHasBlackJack(PlayerHandI player){
        return Consts.BLACK_JACK_SCORE.equals(player.getScore());
    }

    public boolean isPlayerNotHaveBust(PlayerHandI player){
        return player.getScore() <= Consts.BLACK_JACK_SCORE;
    }

    public boolean isPlayerNotHaveBust(List<Card> cards){
        return calcScore(cards) <= Consts.BLACK_JACK_SCORE;
    }

    public boolean isDealerNotHaveMaxScore(PlayerHandI player){
        return player.getScore() <= Consts.DEALER_MAX_SCORE;
    }

    public boolean isDealerNotHaveMaxScore(List<Card> cards){
        return calcScore(cards) <= Consts.DEALER_MAX_SCORE;
    }

    public boolean isPlayersHaveEqualScore(PlayerHandI player, PlayerHandI dealer){
        return player.getScore().equals(dealer.getScore());
    }

    public boolean isPlayerHaveBiggerScore(PlayerHandI player, PlayerHandI dealer) {
        return player.getScore() > dealer.getScore();
    }

    public WinType getWinner(PlayerHandI player, PlayerHandI dealer){
        if (isPlayersHaveEqualScore(player, dealer)){
            return WinType.PUSH;
        } else if (isPlayerHasBlackJack(player) ||
                (isPlayerNotHaveBust(player) && isPlayerHaveBiggerScore(player, dealer)) ){
            return WinType.PLAYER_WIN;
        } else {
            return WinType.DEALER_WIN;
        }
    }

    public static Integer calcScore(List<Card> hand){
        boolean isHandHaveAce = false;
        Integer newScore = 0;
        for (Card card : hand) {
            newScore += card.getCardScore();
            if (card.isCardAce()){
                isHandHaveAce = true;
            }
        }
        if (newScore > Consts.BLACK_JACK_SCORE && isHandHaveAce){
            newScore = 0;
            for (Card card : hand) {
                if (card.isCardAce()){
                    newScore += Consts.DEFAULT_ACE_SCORE;
                } else {
                    newScore += card.getCardScore();
                }
            }
        }
        return newScore;
    }
}
