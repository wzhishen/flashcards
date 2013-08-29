package flashcards;

import java.io.File;
import java.util.LinkedList;

/**
 * @author Zhishen (Jason) Wen and Eric Nida
 * @version Nov 29, 2012
 * MCIT, Penn CIS
 */
/****************************************************
 * The model class for the GUI (Study class).
 ****************************************************
 */
public class StudyModel {
    /**
     ******************** Fields **********************
     */
    private FlashcardsIO io;
    private LinkedList<Card> cardDeck;
    private int time;
    private int goal;
    // the intervals for different kinds of cards
    private static final int EASY_INTERVAL = 20;
    private static final int MODERATE_INTERVAL = 10;
    private static final int DIFFICULT_INTERVAL = 5;
    
    /**
     ****************** Constructor *******************
     */
    public StudyModel() {
        io = new FlashcardsIO();
        cardDeck = io.getCards();
        time = 0;
        goal = -1; // set negative to indicate the end of each play
    }
    
    /**
     ******************** Methods *********************
     */
    /**
     * Sets a card as 'easy'.
     */
    public void setEasy() {
        Card card = cardDeck.removeFirst();
        card.setStatus(false);
        card.setInterval(EASY_INTERVAL);
        cardDeck.add(EASY_INTERVAL, card);
    }
    
    /**
     * Sets a card as 'moderate'.
     */
    public void setModerate() {
        Card card = cardDeck.removeFirst();
        card.setStatus(false);
        card.setInterval(MODERATE_INTERVAL);
        cardDeck.add(MODERATE_INTERVAL, card);
    }
    
    /**
     * Sets a card as 'difficult'.
     */
    public void setDifficult() {
        Card card = cardDeck.removeFirst();
        card.setStatus(false);
        card.setInterval(DIFFICULT_INTERVAL);
        cardDeck.add(DIFFICULT_INTERVAL, card);
    }
    
    /**
     * @param goal The total number of cards 
     * the user wants to study.
     */
    public void setGoal(int goal) {
        this.goal = goal; 
    }
    
    /**
     * @return The next card in the card deck.
     */
    public Card getNextCard() {
        return cardDeck.getFirst();
    }
    
    /**
     * Create a new card deck from specific file object.
     * @param file File that needs to be read in.
     */
    public void createCardDeck(File file) {
        cardDeck = io.getCards(file);
    }
    
    /**
     * @return The total number of cards 
     * the user wants to study.
     */
    public int getGoal() {
        return goal;
    }
    
    /**
     * @return Current time: the number 
     * of cards the user studied.
     */
    public int getTime() {
        return time;
    }
    
    /**
     * @return The size of current card deck.
     */
    public int getCardDeckSize() {
        return cardDeck.size();
    }
    
    /**
     * Let current time advance by 1
     */
    public void advance() {
        time++;
    }
    
    /**
     * Reads cards from file.
     * @return -1 if read unsuccessfully; 0 if file is blank
     * or missing; 1 if read successfully.
     */
    public int read() {
        if (io.getCards() == null) return -1;
        if (this.getCardDeckSize() == 0) return 0;
        return 1;
    }
    
    /**
     * Saves current card deck to a file.
     * @return true if successful; false otherwise.
     */
    public boolean save() {
        io.setCards(cardDeck);
        return io.saveCards();
    }
    
    /**
     ************ Helpers for JUnit Test ************
     */
    /**
     * Sets current card deck to a new card deck.
     * @param cards Card deck that is passed to
     * update current card deck.
     */
    public void setCardDeck(LinkedList<Card> cards) {
        cardDeck = cards;
    }
    
    /**
     * @return true if contents of two studyModel object
     * is the same; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StudyModel)) return false;
        StudyModel that = (StudyModel) o;
        if (this.getTime() != that.getTime() || 
                this.getGoal() != that.getGoal() ||
                this.getCardDeckSize() != that.getCardDeckSize())
            return false;
        for (int i = 0; i < this.getCardDeckSize(); i++)
            if (!this.cardDeck.get(i).equals(that.cardDeck.get(i)))
                return false;
        return true;
    }
    
    /**
     * @return The interval of level 'easy'.
     */
    public static int getEasyInterval() {
        return EASY_INTERVAL;
    }
    
    /**
     * @return The interval of level 'moderate'.
     */
    public static int getModerateInterval() {
        return MODERATE_INTERVAL;
    }
    
    /**
     * @return The interval of level 'difficult'.
     */
    public static int getDifficultInterval() {
        return DIFFICULT_INTERVAL;
    }
    
}
