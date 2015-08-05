package com.testtasks.blackJack.server.component;

import com.testtasks.blackJack.server.common.dao.HandInfoI;
import com.testtasks.blackJack.server.domain.card.Card;
import com.testtasks.blackJack.server.exception.SystemException;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CardGenerator {

    private static final Random CARD_GENERATOR = new Random(System.currentTimeMillis());

    public Card generate(){
        return Card.valueOfCardId(generateCardId());
    }

    /**
     * Сгенерировать карту на основании рук игроков
     */
    public Card generate(HandInfoI handInfo){
        while(true){
            Card newCard = generate();
            if (!handInfo.containsCard(newCard)){
                return newCard;
            };
        }
    }

    protected int generateCardId(){
        return CARD_GENERATOR.nextInt(Card.values().length);
    }
}
