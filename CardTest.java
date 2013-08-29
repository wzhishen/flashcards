package flashcards;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Zhishen (Jason) Wen and Eric Nida
 * @version Nov 29, 2012
 * MCIT, Penn CIS
 */
/**
 * JUnit test for class Card
 */
public class CardTest {
    Card card1, card2, card3;
    Card exp1, exp2, exp3;
    
    @Before
    public void setUp() throws Exception {
        card1 = new Card("Question1", "Answer1", 5, true);
        card2 = new Card("Question2", "Answer2", 10, false);
        card3 = new Card("", "", 20, true);
        exp1 = new Card("Question1", "Answer1", 5, true);
        exp2 = new Card("Question2", "Answer2", 10, false);
        exp3 = new Card("", "", 20, true);
    }

    @Test
    public final void testEqualsObject() {
        assertEquals(card1, exp1);
        assertEquals(card2, exp2);
        assertEquals(card3, exp3);
        assertFalse(card1.equals(card2));
        assertFalse(card1.equals(card3));
        assertFalse(card2.equals(card3));
        assertNotSame(card1, exp1);
        assertNotSame(card2, exp2);
        assertNotSame(card3, exp3);
    }
    
    @Test
    public final void testCardConstructor() {
        assertEquals(card1, exp1);
        assertEquals(card2, exp2);
        assertEquals(card3, exp3);
        assertNotSame(card1, exp1);
        assertNotSame(card2, exp2);
        assertNotSame(card3, exp3);
    }

    @Test
    public final void testGetQuestion() {
        assertEquals("Question1", card1.getQuestion());
        assertEquals("Question2", card2.getQuestion());
        assertEquals("", card3.getQuestion());
        assertEquals(exp1.getQuestion(), card1.getQuestion());
        assertEquals(exp2.getQuestion(), card2.getQuestion());
        assertEquals(exp3.getQuestion(), card3.getQuestion());
    }

    @Test
    public final void testGetAnswer() {
        assertEquals("Answer1", card1.getAnswer());
        assertEquals("Answer2", card2.getAnswer());
        assertEquals("", card3.getAnswer());
        assertEquals(exp1.getAnswer(), card1.getAnswer());
        assertEquals(exp2.getAnswer(), card2.getAnswer());
        assertEquals(exp3.getAnswer(), card3.getAnswer());
    }

    @Test
    public final void testGetInterval() {
        assertEquals(5, card1.getInterval());
        assertEquals(10, card2.getInterval());
        assertEquals(20, card3.getInterval());
        assertEquals(exp1.getInterval(), card1.getInterval());
        assertEquals(exp2.getInterval(), card2.getInterval());
        assertEquals(exp3.getInterval(), card3.getInterval());
    }

    @Test
    public final void testGetStatus() {
        assertEquals(true, card1.getStatus());
        assertEquals(false, card2.getStatus());
        assertEquals(true, card3.getStatus());
        assertEquals(exp1.getStatus(), card1.getStatus());
        assertEquals(exp2.getStatus(), card2.getStatus());
        assertEquals(exp3.getStatus(), card3.getStatus());
    }

    @Test
    public final void testSetQuestion() {
        card1.setQuestion("NewQuestion1");
        card2.setQuestion("NewQuestion2");
        card3.setQuestion("NewQuestion3");
        assertEquals("NewQuestion1", card1.getQuestion());
        assertEquals("NewQuestion2", card2.getQuestion());
        assertEquals("NewQuestion3", card3.getQuestion());
        assertFalse(card1.getQuestion().equals(exp1.getQuestion()));
        assertFalse(card2.getQuestion().equals(exp2.getQuestion()));
        assertFalse(card3.getQuestion().equals(exp3.getQuestion()));
    }

    @Test
    public final void testSetAnswer() {
        card1.setAnswer("NewAnswer1");
        card2.setAnswer("NewAnswer2");
        card3.setAnswer("NewAnswer3");
        assertEquals("NewAnswer1", card1.getAnswer());
        assertEquals("NewAnswer2", card2.getAnswer());
        assertEquals("NewAnswer3", card3.getAnswer());
        assertFalse(card1.getAnswer().equals(exp1.getAnswer()));
        assertFalse(card2.getAnswer().equals(exp2.getAnswer()));
        assertFalse(card3.getAnswer().equals(exp3.getAnswer()));
    }

    @Test
    public final void testSetInterval() {
        card1.setInterval(20);
        card2.setInterval(5);
        card3.setInterval(10);
        assertEquals(20, card1.getInterval());
        assertEquals(5, card2.getInterval());
        assertEquals(10, card3.getInterval());
        assertFalse(card1.getInterval() == exp1.getInterval());
        assertFalse(card2.getInterval() == exp2.getInterval());
        assertFalse(card3.getInterval() == exp3.getInterval());
    }

    @Test
    public final void testSetStatus() {
        card1.setStatus(false);
        card2.setStatus(true);
        card3.setStatus(false);
        assertEquals(false, card1.getStatus());
        assertEquals(true, card2.getStatus());
        assertEquals(false, card3.getStatus());
        assertFalse(card1.getStatus() == exp1.getStatus());
        assertFalse(card2.getStatus() == exp2.getStatus());
        assertFalse(card3.getStatus() == exp3.getStatus());
    }

}
