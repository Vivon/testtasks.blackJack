package com.testtasks.blackJack.server.component;

import com.testtasks.blackJack.server.common.dao.HandInfoI;
import com.testtasks.blackJack.server.domain.card.Card;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class CardGeneratorTest {

    private CardGenerator cardGenerator;

    @Before
    public void initDataForTests(){
        cardGenerator = spy(new CardGenerator());
    }

    @Test
    public void testMethodGenerateCardId(){
        Integer result1 = cardGenerator.generateCardId();
        Integer result2 = cardGenerator.generateCardId();
        Integer result3 = cardGenerator.generateCardId();
        if (result1.equals(result2) || result2.equals(result3) || result3.equals(result1)){
            fail();
        }
    }

    @Test
    public void testMethodGenerate(){
        Card cardForTest = Card.CRUSADE_ACE;
        when(cardGenerator.generateCardId()).thenReturn(cardForTest.getCardId());
        assertEquals(cardForTest, cardGenerator.generate());
    }

    @Test
    public void testMethodGenerateWhenGeneratedNotValidId(){
        when(cardGenerator.generateCardId()).thenReturn(100);
        try {
            cardGenerator.generate();
            fail();
        }catch (IllegalArgumentException ex){
            assertEquals("Не верный ключ карты!", ex.getMessage());
        }
    }

    @Test
    public void testMethodGenerateWithHandInfo(){
        Card cardForTest = Card.SPADES_TWO;
        Card cardForTest2 = Card.CRUSADE_EIGHT;

        HandInfoI handInfoMock = mock(HandInfoI.class);
        when(handInfoMock.containsCard(cardForTest)).thenReturn(true);
        when(handInfoMock.containsCard(cardForTest2)).thenReturn(false);
        when(cardGenerator.generate()).thenReturn(cardForTest).thenReturn(cardForTest2);
        assertEquals(cardForTest2, cardGenerator.generate(handInfoMock));
        verify(cardGenerator, times(3)).generate();
    }
}
