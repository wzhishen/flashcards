package flashcards;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

/**
 * @author Zhishen (Jason) Wen and Eric Nida
 * @version Nov 29, 2012
 * MCIT, Penn CIS
 */
/**
 * JUnit test for class StudyModel
 */
public class StudyModelTest {
    Card card1, card2, card3, card4;
    LinkedList<Card> cardDeck1, cardDeck2;
    StudyModel studyModel1, studyModel2;

    @Before
    public void setUp() throws Exception {
        cardDeck1 = new LinkedList<Card>();
        cardDeck2 = new LinkedList<Card>();
        studyModel1 = new StudyModel();
        studyModel2 = new StudyModel();
        cardDeck1.add(new Card("Question1", "Answer1", 5, true));
        cardDeck1.add(new Card("Question2", "Answer2", 10, true));
        cardDeck1.add(new Card("Question3", "Answer3", 20, false));
        cardDeck1.add(new Card("Question4", "Answer4", 10, false));
        cardDeck1.add(new Card("Question5", "Answer5", 20, true));
        cardDeck1.add(new Card("Question6", "Answer6", 5, false));
        studyModel1.setCardDeck(cardDeck1);
    }

    @Test
    public final void testStudyModelConstructor() {
        cardDeck2.add(new Card("Question1", "Answer1", 5, true));
        cardDeck2.add(new Card("Question2", "Answer2", 10, true));
        cardDeck2.add(new Card("Question3", "Answer3", 20, false));
        cardDeck2.add(new Card("Question4", "Answer4", 10, false));
        cardDeck2.add(new Card("Question5", "Answer5", 20, true));
        cardDeck2.add(new Card("Question6", "Answer6", 5, false));
        studyModel2.setCardDeck(cardDeck2);
        
        assertEquals(studyModel1, studyModel2);
        assertFalse(studyModel1.equals(new StudyModel()));
        assertNotSame(studyModel1, studyModel2);
    }

    @Test
    public final void testSetCardDeck() {
        cardDeck2.add(new Card("NewQuestion1", "NewAnswer1", 10, false));
        cardDeck2.add(new Card("NewQuestion2", "NewAnswer2", 5, false));
        cardDeck2.add(new Card("NewQuestion3", "NewAnswer3", 5, true));
        cardDeck2.add(new Card("NewQuestion4", "NewAnswer4", 20, true));
        cardDeck2.add(new Card("NewQuestion5", "NewAnswer5", 20, true));
        cardDeck2.add(new Card("NewQuestion6", "NewAnswer6", 5, false));
        studyModel2.setCardDeck(cardDeck2);
        
        assertFalse(studyModel1.equals(studyModel2));
        assertNotSame(studyModel1, studyModel2);
    }

    @Test
    public final void testSetEasy() {
        LinkedList<Card> cardDeck1 = generateSameCards(
                30, "Question", "Answer", StudyModel.getDifficultInterval(), true);
        studyModel1.setCardDeck(cardDeck1);
        studyModel1.setEasy();
        
        LinkedList<Card> cardDeck2 = generateSameCards(
                29, "Question", "Answer", StudyModel.getDifficultInterval(), true);
        cardDeck2.add(
                StudyModel.getEasyInterval(), 
                new Card("Question", "Answer", StudyModel.getEasyInterval(), false));
        studyModel2.setCardDeck(cardDeck2);
        
        assertEquals(studyModel1, studyModel2);
        assertNotSame(studyModel1, studyModel2);
    }

    @Test
    public final void testSetModerate() {
        LinkedList<Card> cardDeck1 = generateSameCards(
                30, "Question", "Answer", StudyModel.getDifficultInterval(), true);
        studyModel1.setCardDeck(cardDeck1);
        studyModel1.setModerate();
        
        LinkedList<Card> cardDeck2 = generateSameCards(
                29, "Question", "Answer", StudyModel.getDifficultInterval(), true);
        cardDeck2.add(
                StudyModel.getModerateInterval(), 
                new Card("Question", "Answer", StudyModel.getModerateInterval(), false));
        studyModel2.setCardDeck(cardDeck2);
        
        assertEquals(studyModel1, studyModel2);
        assertNotSame(studyModel1, studyModel2);
    }

    @Test
    public final void testSetDifficult() {
        LinkedList<Card> cardDeck1 = generateSameCards(
                30, "Question", "Answer", StudyModel.getModerateInterval(), true);
        studyModel1.setCardDeck(cardDeck1);
        studyModel1.setDifficult();
        
        LinkedList<Card> cardDeck2 = generateSameCards(
                29, "Question", "Answer", StudyModel.getModerateInterval(), true);
        cardDeck2.add(
                StudyModel.getDifficultInterval(), 
                new Card("Question", "Answer", StudyModel.getDifficultInterval(), false));
        studyModel2.setCardDeck(cardDeck2);
        
        assertEquals(studyModel1, studyModel2);
        assertNotSame(studyModel1, studyModel2);
    }

    @Test
    public final void testSetGoal() {
        assertEquals(-1, studyModel1.getGoal());
        for (int i = 1; i <= 50; i++) {
            studyModel1.setGoal(i);
            assertEquals(i, studyModel1.getGoal());
        }
    }

    @Test
    public final void testGetNextCard() {
        Card nextCard = studyModel1.getNextCard();
        assertEquals(new Card("Question1", "Answer1", 5, true), nextCard);
        assertNotSame(new Card("Question1", "Answer1", 5, true), nextCard);
        
        studyModel1.setDifficult();
        nextCard = studyModel1.getNextCard();
        assertEquals(new Card("Question2", "Answer2", 10, true), nextCard);
        assertNotSame(new Card("Question2", "Answer2", 10, true), nextCard);
        
        studyModel1.setDifficult();
        nextCard = studyModel1.getNextCard();
        assertEquals(new Card("Question3", "Answer3", 20, false), nextCard);
        assertNotSame(new Card("Question3", "Answer3", 20, false), nextCard);
        
        studyModel1.setDifficult();
        nextCard = studyModel1.getNextCard();
        assertEquals(new Card("Question4", "Answer4", 10, false), nextCard);
        assertNotSame(new Card("Question4", "Answer4", 10, false), nextCard);
    }

    @Test
    public final void testGetGoal() {
        assertEquals(-1, studyModel1.getGoal());
        for (int i = 1; i <= 50; i++) {
            studyModel1.setGoal(i);
            assertEquals(i, studyModel1.getGoal());
        }
    }

    @Test
    public final void testGetTime() {
        assertEquals(0, studyModel1.getTime());
        for (int i = 0; i < 50; i++) studyModel1.advance();
        assertEquals(50, studyModel1.getTime());
    }

    @Test
    public final void testGetCardDeckSize() {
        assertEquals(cardDeck1.size(), studyModel1.getCardDeckSize());
        
        cardDeck1.addAll(generateSameCards(20, "Q", "A", StudyModel.getDifficultInterval(), true));
        studyModel1.setCardDeck(cardDeck1);
        assertEquals(cardDeck1.size(), studyModel1.getCardDeckSize());
    }

    @Test
    public final void testAdvance() {
        for (int i = 1; i <= 50; i++) {
            studyModel1.advance();
            assertEquals(i, studyModel1.getTime());
        }
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
