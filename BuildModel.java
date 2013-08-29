package flashcards;

import java.io.File;
import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * BuildModel.java
 * -------------------------------------------------------------
 * Authors: Zhishen Wen and Eric Nida
 * Model for use with Build GUI.  Methods will be used in conjunction with
 * with card objects and Build GUI components.
 */

public class BuildModel {
    
    /**
     ******************** Fields **********************
     */
    private FlashcardsIO io;
    private LinkedList<Card> cardDeck;
    private static int cardCounter;
    private Card currentCard;
    // the intervals for different kinds of cards
    private static final int EASY_INTERVAL = 20;
    private static final int MODERATE_INTERVAL = 10;
    private static final int DIFFICULT_INTERVAL = 5;
    
    /**
     ****************** Constructor *******************
     */
    public BuildModel() {
        io = new FlashcardsIO();
        cardDeck = io.getCards();
        cardCounter = 0;
        
        if (getCardDeckSize() > 0)
            currentCard = cardDeck.get(cardCounter);
        else currentCard = new Card("", "", MODERATE_INTERVAL, true);
    }
    
    /**
     * @return the current card.
     */
    public Card getCurrentCard() {
        return currentCard;
    }
    
    /**
     * Update the current card by getting the card at the index
     * specified by the cardCounter variable.
     */
    public void updateCurrentCard() {
        currentCard = cardDeck.get(cardCounter);
    }
    
    /**
     * Create a new card deck LinkedList by reading in data from file.
     * @param file File object to be read in, used to create new card deck.
     */
    public void createCardDeck(File file) {
        cardDeck = io.getCards(file);
        updateCurrentCard();
    }
    
    /**
     * @return the size of the cardDeck.
     */
    public int getCardDeckSize() {
        return cardDeck.size();
    }
    
    /**
     * @return the cardDeck LinkedList.
     */
    public LinkedList<Card> getCardDeck() {
        return cardDeck;
    }
    
    /**
     * @return scheduled time of next card presentation, which
     * is the card index in the deck plus one. 
     */
    public int getScheduledTime(Card card) {
        return cardDeck.indexOf(card) + 1;
    }
    
    /**
     * @return scheduled time using currentCard as argument.
     */
    public int getScheduledTime() {
        return getScheduledTime(currentCard);
    }
    
    /**
     * @return the current value of the cardCounter variable.
     */
    public static int getCurrentCounter() {
        return cardCounter;
    }
    
    /**
     * Current card will be set to the next card object in the deck
     * by first incrementing the cardCounter and then getting card at the
     * updated index.
     */
    public void setNextCard() {
        incrementCounter();
        currentCard = cardDeck.get(cardCounter);
    }
       
    /**
     * Current card will be set to the previous card object in the deck
     * by first decrementing the cardCounter and then getting card at the
     * updated index.
     */
    public void setPreviousCard() {
        decrementCounter();
        currentCard = cardDeck.get(cardCounter);
    }
    
    /**
     * @param card Card object that will be set as current card.
     */
    public void setCurrentCard(Card card) {
        currentCard = card;
    }
    
    /**
     * Sets every card in deck to virgin by calling the setStatus
     * method for every card and setting each isVirgin status to true.
     */
    public void setAllCardsVirgin() {
        for (Card card : cardDeck)
            card.setStatus(true);
    }
    
    /**
     * Sorts card deck by comparing card questions.
     */
    public void sortCards() {
        Collections.sort(cardDeck, new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return Collator.getInstance().compare(
                        o1.getQuestion(), 
                        o2.getQuestion());
            }
        });
    }
    
    /**
     * Compares whether string parameter is equal to a question of
     * any card in the deck and returns boolean value.
     * @param s String to compare to questions of cards in deck.
     */
    public boolean hasSameQuestion(String s) {
        for (Card card : cardDeck)
            if (card.getQuestion().equalsIgnoreCase(s)) return true;
        return false;
    }
    
    /**
     * Compares whether string is equal to either a question or answer
     * of any card currently in the deck and if so returns that card object.
     * Otherwise returns null.
     * @param s String used to find either question or answer in card deck.
     */
    public Card findCard(String s) {
        for (Card card : cardDeck)
            if (card.getQuestion().toLowerCase().contains(s.toLowerCase()) || 
                    card.getAnswer().toLowerCase().contains(s.toLowerCase()))
                return card;
        return null;
    }
    
    /**
     * Set counter to specified value.
     * @param i The integer value to which cardCounter will be set.
     */
    public void setCounter(int i) {
        cardCounter = i;
    }
    
    /**
     * Add one to the current cardCounter value.
     */
    public void incrementCounter() {
        cardCounter++;
    }
    
    /**
     * Subtract one from the current cardCounter value.
     */
    public void decrementCounter() {
        cardCounter--;
    }
    
    /**
     * Add card object to the card deck.
     * @ param card Card object that will be added to deck.
     */
    public void addCard(Card card) {
        cardDeck.add(card);
    }
    
    /**
     * Add card object to specified index in card deck.
     * @param card Card object that will be added to deck.
     * @param index Value of index where card will be added to deck.
     */
    public void addCard(Card card, int index) {
        cardDeck.add(index - 1, card);
    }
    
    /**
     * Remove specified card from deck.
     * @param card Card object to remove from deck.
     */
    public void removeCard(Card card) {
        cardDeck.remove(card);
    }
    
    /**
     * Deletes all cards from the deck.
     */
    public void removeAllCards() {
        cardDeck.clear();
    }
    
    /**
     * If default card file does not exist then return -1, and if
     * current card deck size is zero then return 0.  Otherwise, return 1.
     * 
     */
    public int read() {
        if (io.getCards() == null) return -1;
        if (this.getCardDeckSize() == 0) return 0;
        return 1;
    }
    
    /**
     * Saves card deck to file.
     * @return true if card deck was successfully saved and false otherwise.
     */
    public boolean save() {
        io.setCards(cardDeck);
        return io.saveCards();
    }
    
    /**
     * Saves card deck to selected file.
     * @param file File object to which to save card deck.
     * @return true if card deck was successfully saved and false otherwise.
     */
    public boolean save(File file) {
        io.setCards(cardDeck);
        return io.saveCards(file);
    }
    
    /**
     * @return the integer value of the constant EASY_INTERVAL.
     */
    public static int getEasyInterval() {
        return EASY_INTERVAL;
    }
    
    /**
     * @return the integer value of the constant MODERATE_INTERVAL.
     */
    public static int getModerateInterval() {
        return MODERATE_INTERVAL;
    }
    
    /**
     * @return the integer value of the constant DIFFICULT_INTERVAL.
     */
    public static int getDifficultInterval() {
        return DIFFICULT_INTERVAL;
    }

    /**
     ************ Helpers for JUnit Test ************
     */
    public void setCardDeck(LinkedList<Card> cards) {
        cardDeck = cards;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BuildModel)) return false;
        BuildModel that = (BuildModel) o;
        if (this.getScheduledTime() != that.getScheduledTime() || 
                this.getCardDeckSize() != that.getCardDeckSize())
            return false;
        for (int i = 0; i < this.getCardDeckSize(); i++)
            if (!this.cardDeck.get(i).equals(that.cardDeck.get(i)))
                return false;
        return true;
    }
    
}
