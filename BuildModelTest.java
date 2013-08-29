package flashcards;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * BuildModelTest.java
 * -------------------------------------------------------------
 * Authors: Zhishen Wen and Eric Nida
 * JUnit test file for BuildModel.
 */

public class BuildModelTest {
    Card firstCard, secondCard, thirdCard, fourthCard;
    LinkedList<Card> cardDeck1, cardDeck2;
    BuildModel buildModel1, buildModel2;

    @Before
    public void setUp() throws Exception {
        cardDeck1 = new LinkedList<Card>();
        cardDeck2 = new LinkedList<Card>();
        buildModel1 = new BuildModel();
        buildModel2 = new BuildModel();
        cardDeck1.add(new Card("Question1", "Answer1", 5, true));
        cardDeck1.add(new Card("Question2", "Answer2", 10, true));
        cardDeck1.add(new Card("Question3", "Answer3", 20, false));
        cardDeck1.add(new Card("Question4", "Answer4", 10, false));
        cardDeck1.add(new Card("Question5", "Answer5", 20, true));
        cardDeck1.add(new Card("Question6", "Answer6", 5, false));
        buildModel1.setCardDeck(cardDeck1);
    }

    @Test
    public void testBuildModelConstructor() {
        cardDeck2.add(new Card("Question1", "Answer1", 5, true));
        cardDeck2.add(new Card("Question2", "Answer2", 10, true));
        cardDeck2.add(new Card("Question3", "Answer3", 20, false));
        cardDeck2.add(new Card("Question4", "Answer4", 10, false));
        cardDeck2.add(new Card("Question5", "Answer5", 20, true));
        cardDeck2.add(new Card("Question6", "Answer6", 5, false));
        buildModel2.setCardDeck(cardDeck2);
        
        assertEquals(buildModel1, buildModel2);
        assertFalse(buildModel1.equals(new BuildModel()));
        assertNotSame(buildModel1, buildModel2);
    }

    @Test
    public void testSetCardDeck() {
        cardDeck2.add(new Card("Question1", "Answer1", 5, true));
        cardDeck2.add(new Card("Question2", "Answer2", 10, true));
        cardDeck2.add(new Card("Question3", "Answer3", 20, false));
        cardDeck2.add(new Card("Question4", "Answer4", 10, false));
        cardDeck2.add(new Card("Question5", "Answer5", 20, true));
        cardDeck2.add(new Card("Question6", "Answer6", 5, false));
        buildModel2.setCardDeck(cardDeck2);
        assertEquals(buildModel1, buildModel2);
        cardDeck2.add(new Card("NewQuestion7", "NewAnswer7", 5, false));
        assertFalse(buildModel1.equals(buildModel2));
        assertNotSame(buildModel1, buildModel2);
    }
    
    @Test
    public void testGetCurrentCard() {
        Card blankCard = new Card("", "", 10, true);
        buildModel2.setCurrentCard(blankCard);
        assertEquals(blankCard, buildModel2.getCurrentCard());
        firstCard = new Card("Question1", "Answer1", 5, true); 
        secondCard = new Card("Question2", "Answer2", 10, true);
        cardDeck2.add(firstCard);
        buildModel2.setCardDeck(cardDeck2);
        buildModel2.setCurrentCard(firstCard);
        assertEquals(firstCard, buildModel2.getCurrentCard());   
        buildModel2.setCurrentCard(secondCard);
        assertEquals(secondCard, buildModel2.getCurrentCard()); 
    }

    @Test
    public void testUpdateCurrentCard() {
        firstCard = new Card("Question1", "Answer1", 5, true); 
        secondCard = new Card("Question2", "Answer2", 10, true);
        cardDeck2.add(firstCard);
        cardDeck2.add(secondCard);
        buildModel2.setCardDeck(cardDeck2);
        buildModel2.updateCurrentCard();
        assertEquals(buildModel2.getCurrentCard(), firstCard);
        buildModel2.incrementCounter();
        buildModel2.updateCurrentCard();
        assertEquals(buildModel2.getCurrentCard(), secondCard);
    }
    
    @Test
    public void testGetCardDeckSize() {
        assertEquals(buildModel1.getCardDeckSize(), cardDeck1.size());
        assertEquals(buildModel1.getCardDeckSize(), 6);
        cardDeck1.addAll(generateSameCards(20, "Q", "A",
                StudyModel.getDifficultInterval(), true));
        buildModel1.setCardDeck(cardDeck1);
        assertEquals(buildModel1.getCardDeckSize(), cardDeck1.size());
        assertEquals(buildModel1.getCardDeckSize(), 26);
        buildModel2.setCardDeck(cardDeck2);
        assertEquals(buildModel2.getCardDeckSize(), 0);
    }
    
    @Test
    public void testGetCardDeck() {
        assertEquals(buildModel1.getCardDeck(), cardDeck1);
        assertSame(buildModel1.getCardDeck(), cardDeck1);
    }

    @Test
    public void testGetScheduledTimeCard() {
        firstCard = new Card("Question1", "Answer1", 5, true);
        secondCard = new Card("Question2", "Answer2", 10, true);
        thirdCard = new Card("Question3", "Answer3", 20, false);
        cardDeck2.add(firstCard);
        cardDeck2.add(secondCard);
        cardDeck2.add(thirdCard);
        buildModel2.setCardDeck(cardDeck2);
        assertEquals(buildModel2.getScheduledTime(firstCard), 1);
        assertEquals(buildModel2.getScheduledTime(thirdCard), 3);
    }

    @Test
    public void testGetScheduledTime() {
        firstCard = new Card("Question1", "Answer1", 5, true);
        secondCard = new Card("Question2", "Answer2", 10, true);
        thirdCard = new Card("Question3", "Answer3", 20, false);
        cardDeck2.add(firstCard);
        cardDeck2.add(secondCard);
        cardDeck2.add(thirdCard);
        buildModel2.setCardDeck(cardDeck2);
        buildModel2.setCurrentCard(firstCard);
        assertEquals(buildModel2.getScheduledTime(), 1);
        buildModel2.setCurrentCard(thirdCard);
        assertEquals(buildModel2.getScheduledTime(), 3);
    }

    @SuppressWarnings("static-access")
    @Test
    public void testGetCurrentCounter() {
        assertEquals(BuildModel.getCurrentCounter(), 0);
        assertEquals(buildModel1.getCurrentCounter(), 0);
        buildModel1.incrementCounter();
        assertEquals(buildModel1.getCurrentCounter(), 1);
    }

    @SuppressWarnings("static-access")
    @Test
    public void testSetNextCard() {
        firstCard = new Card("Question1", "Answer1", 5, true);
        secondCard = new Card("Question2", "Answer2", 10, true);
        cardDeck2.add(firstCard);
        cardDeck2.add(secondCard);
        buildModel2.setCardDeck(cardDeck2);
        buildModel2.setCurrentCard(firstCard);
        assertEquals(buildModel2.getCurrentCard(), firstCard);
        buildModel2.setNextCard();
        assertEquals(buildModel2.getCurrentCounter(), 1);
        assertEquals(buildModel2.getCurrentCard(), secondCard);
    }

    @SuppressWarnings("static-access")
    @Test
    public void testSetPreviousCard() {
        firstCard = new Card("Question1", "Answer1", 5, true);
        secondCard = new Card("Question2", "Answer2", 10, true);
        cardDeck2.add(firstCard);
        cardDeck2.add(secondCard);
        buildModel2.setCardDeck(cardDeck2);
        buildModel2.incrementCounter();
        buildModel2.setCurrentCard(secondCard);
        buildModel2.setPreviousCard();
        assertEquals(buildModel2.getCurrentCard(), firstCard);
        assertEquals(buildModel2.getCurrentCounter(), 0);
    }

    @Test
    public void testSetCurrentCard() {
        firstCard = new Card("Question1", "Answer1", 5, true);
        secondCard = new Card("Question2", "Answer2", 10, true);
        buildModel2.setCurrentCard(firstCard);
        assertEquals(buildModel2.getCurrentCard(), firstCard);
        assertSame(buildModel2.getCurrentCard(), firstCard);
        buildModel2.setCurrentCard(secondCard);
        assertEquals(buildModel2.getCurrentCard(), secondCard);  
        assertSame(buildModel2.getCurrentCard(), secondCard);
    }

    @Test
    public void testSetAllCardsVirgin() {
        cardDeck2.add(new Card("Question1", "Answer1", 5, true));
        cardDeck2.add(new Card("Question2", "Answer2", 10, true));
        cardDeck2.add(new Card("Question3", "Answer3", 20, true));
        cardDeck2.add(new Card("Question4", "Answer4", 10, true));
        cardDeck2.add(new Card("Question5", "Answer5", 20, true));
        cardDeck2.add(new Card("Question6", "Answer6", 5, true));
        buildModel2.setCardDeck(cardDeck2);
        assertFalse(buildModel1.equals(buildModel2));
        buildModel1.setAllCardsVirgin();
        assertTrue(buildModel1.equals(buildModel2));
        assertEquals(buildModel1, buildModel2);
        assertNotSame(buildModel1, buildModel2);
    }

    @Test
    public void testSortCards() {
        cardDeck2.add(new Card("Question6", "Answer6", 5, false));
        cardDeck2.add(new Card("Question4", "Answer4", 10, false));
        cardDeck2.add(new Card("Question2", "Answer2", 10, true));
        cardDeck2.add(new Card("Question3", "Answer3", 20, false));
        cardDeck2.add(new Card("Question5", "Answer5", 20, true));
        cardDeck2.add(new Card("Question1", "Answer1", 5, true));
        buildModel2.setCardDeck(cardDeck2);
        assertFalse(buildModel1.equals(buildModel2));
        buildModel2.sortCards();
        assertEquals(buildModel1, buildModel2);       
        assertNotSame(buildModel1, buildModel2);
    }

    @Test
    public void testHasSameQuestion() {
        assertTrue(buildModel1.hasSameQuestion("Question1"));
        assertFalse(buildModel1.hasSameQuestion("Question99"));
    }

    @Test
    public void testFindCard() {
        firstCard = new Card("Question1", "Answer1", 5, true);
        secondCard = new Card("Question2", "Answer2", 10, true);
        cardDeck2.add(firstCard);
        cardDeck2.add(secondCard);
        buildModel2.setCardDeck(cardDeck2);
        assertEquals(buildModel2.findCard("Question1"), firstCard);
        assertEquals(buildModel2.findCard("Answer1"), firstCard);
        assertEquals(buildModel2.findCard("Question2"), secondCard);
        assertNull(buildModel2.findCard("Question99"));
    }
    
    @SuppressWarnings("static-access")
    @Test
    public void testSetCounter() {
        assertEquals(buildModel1.getCurrentCounter(), 0);
        buildModel1.setCounter(5);
        assertEquals(buildModel1.getCurrentCounter(), 5);
    }

    @SuppressWarnings("static-access")
    @Test
    public void testIncrementCounter() {
        assertEquals(buildModel1.getCurrentCounter(), 0);
        buildModel1.incrementCounter();
        assertEquals(buildModel1.getCurrentCounter(), 1);
        for (int i = 0; i < 99; i++) buildModel1.incrementCounter();
        assertEquals(buildModel1.getCurrentCounter(), 100);
    }

    @SuppressWarnings("static-access")
    @Test
    public void testDecrementCounter() {
        assertEquals(buildModel1.getCurrentCounter(), 0);
        buildModel1.decrementCounter();
        assertEquals(buildModel1.getCurrentCounter(), -1);
        for (int i = 0; i < 101; i++) buildModel1.incrementCounter();
        assertEquals(buildModel1.getCurrentCounter(), 100);
        buildModel1.decrementCounter();
        assertEquals(buildModel1.getCurrentCounter(), 99);        
    }

    @Test
    public void testAddCardCard() {
        cardDeck2.add(new Card("Question1", "Answer1", 5, true));
        cardDeck2.add(new Card("Question2", "Answer2", 10, true));
        cardDeck2.add(new Card("Question3", "Answer3", 20, false));
        cardDeck2.add(new Card("Question4", "Answer4", 10, false));
        cardDeck2.add(new Card("Question5", "Answer5", 20, true));
        buildModel2.setCardDeck(cardDeck2);
        assertFalse(buildModel2.equals(buildModel1));
        buildModel2.addCard(new Card("Question6", "Answer6", 5, false));
        assertEquals(buildModel1, buildModel2);
        assertNotSame(buildModel1, buildModel2);
    }

    @Test
    public void testAddCardCardInt() {
        cardDeck2.add(new Card("Question1", "Answer1", 5, true));
        cardDeck2.add(new Card("Question2", "Answer2", 10, true));
        cardDeck2.add(new Card("Question4", "Answer4", 10, false));
        cardDeck2.add(new Card("Question5", "Answer5", 20, true));
        cardDeck2.add(new Card("Question6", "Answer6", 5, false));
        buildModel2.setCardDeck(cardDeck2);
        assertFalse(buildModel2.equals(buildModel1));
        buildModel2.addCard(new Card("Question3", "Answer3", 20, false), 3);
        assertEquals(buildModel1, buildModel2);
        assertNotSame(buildModel1, buildModel2);
    }

    @Test
    public void testRemoveCard() {
        firstCard = new Card("Question0", "Answer0", 5, false);
        cardDeck2.add(firstCard);
        cardDeck2.add(new Card("Question1", "Answer1", 5, true));
        cardDeck2.add(new Card("Question2", "Answer2", 10, true));
        cardDeck2.add(new Card("Question3", "Answer3", 20, false));
        cardDeck2.add(new Card("Question4", "Answer4", 10, false));
        cardDeck2.add(new Card("Question5", "Answer5", 20, true));
        cardDeck2.add(new Card("Question6", "Answer6", 5, false));
        buildModel2.setCardDeck(cardDeck2);
        assertFalse(buildModel2.equals(buildModel1));
        buildModel2.removeCard(firstCard);
        assertEquals(buildModel1, buildModel2);
        assertNotSame(buildModel1, buildModel2);
    }
    
    @Test
    public void testRemoveAllCards() {
        LinkedList<Card> emptyDeck = new LinkedList<Card>();
        firstCard = new Card("Question0", "Answer0", 5, false);
        cardDeck2.add(firstCard);
        buildModel2.setCardDeck(cardDeck2);
        assertEquals(buildModel2.getCardDeck(), cardDeck2);
        buildModel2.removeAllCards();
        assertEquals(buildModel2.getCardDeck(), cardDeck2);
        assertEquals(buildModel2.getCardDeck(), emptyDeck);
        assertNotSame(buildModel2.getCardDeck(), emptyDeck);
        assertFalse(buildModel2.getCardDeck() == cardDeck1);
    }

    @Test
    public void testRead() {
        buildModel2.setCardDeck(cardDeck2);
        assertEquals(buildModel2.read(), 0);
        assertEquals(buildModel1.read(), 1);
    }

    @Test
    public void testSave() {
        assertTrue(buildModel1.save());
    }

    @Test
    public void testGetEasyInterval() {
        assertEquals(BuildModel.getEasyInterval(), 20);
    }

    @Test
    public void testGetModerateInterval() {
        assertEquals(BuildModel.getModerateInterval(), 10);
    }

    @Test
    public void testGetDifficultInterval() {
        assertEquals(BuildModel.getDifficultInterval(), 5);
    }
    
    /************************** Helper **************************/
    private LinkedList<Card> generateSameCards(int number, 
                                               String question, 
                                               String answer, 
                                               int interval, 
                                               boolean b) {
        LinkedList<Card> newCards = new LinkedList<>();
        for (int i = 0; i < number; i++)
            newCards.add(new Card(question, answer, interval, b));
        return newCards;
    }

}
