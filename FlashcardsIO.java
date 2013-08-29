package flashcards;

import java.io.*;
import java.nio.file.*;
import java.util.LinkedList;

/**
 * @author Zhishen (Jason) Wen and Eric Nida
 * @version Nov 29, 2012
 * MCIT, Penn CIS
 */
/********************************************************************
 * Class that handles input/output.
 * Data is saved as a delimited text file;
 * tab character (\t) is used as delimiter for fields,
 * new line character (\n) is used as delimiter for each card record.
 ********************************************************************
 */
public class FlashcardsIO {
    /**
     ******************** Fields **********************
     */
    private LinkedList<Card> cards = null;
    private Path cardsPath = null;
    private File cardsFile = null;
    private final String FIELD_SEPERATOR = "\t"; // delimiter for fields
    
    /**
     ****************** Constructor *******************
     */
    public FlashcardsIO() {
        cardsPath = Paths.get("data.txt"); // default path to save data file
        cardsFile = cardsPath.toFile();
        cards = this.getCards();
    }
    
    /**
     ******************** Methods *********************
     * code partially adapted from P581, Chapter 18,
     * Murach's Java Programming (4th ed.)
     **************************************************
     */
    /**
     * @return A linked list of cards saved in a text file
     * if read successfully; null otherwise.
     */
    public LinkedList<Card> getCards() {
        
        if (cards != null) return cards;
        
        cards = new LinkedList<>();
        
        if (Files.exists(cardsPath)) {
            try (BufferedReader in =
                    new BufferedReader(
                    new FileReader(cardsFile))) {
                String line = in.readLine();
                while (line != null) {
                    String[] fields = line.split(FIELD_SEPERATOR);
                    String question = fields[0];
                    String answer = fields[1];
                    int interval = Integer.parseInt(fields[2]);
                    boolean isVirgin = Boolean.parseBoolean(fields[3]);
                    Card card = new Card(question, answer, interval, isVirgin);
                    cards.add(card);
                    line = in.readLine();
                }
            }
            catch(IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return cards;
    }
    
    /**
     * Overloaded version of saveCards.
     * @return A linked list of cards saved in a text file
     * if read successfully; null otherwise.
     */
    public LinkedList<Card> getCards(File file) {
        
        cards = new LinkedList<>();
        
        if (Files.exists(file.toPath())) {
            try (BufferedReader in =
                    new BufferedReader(
                    new FileReader(file))) {
                String line = in.readLine();
                while (line != null) {
                    String[] fields = line.split(FIELD_SEPERATOR);
                    String question = fields[0];
                    String answer = fields[1];
                    int interval = Integer.parseInt(fields[2]);
                    boolean isVirgin = Boolean.parseBoolean(fields[3]);
                    Card card = new Card(question, answer, interval, isVirgin);
                    cards.add(card);
                    line = in.readLine();
                }
            }
            catch(IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return cards;
    }
    
    /**
     * Saves current card deck to a text file.
     * @return true if successful; false otherwise.
     */
    public boolean saveCards() {
        try (PrintWriter out = new PrintWriter(
                               new BufferedWriter(
                               new FileWriter(cardsFile)))) {
            for (Card card : cards) {
                out.print(card.getQuestion() + FIELD_SEPERATOR);
                out.print(card.getAnswer() + FIELD_SEPERATOR);
                out.print(card.getInterval() + FIELD_SEPERATOR);
                out.println(card.getStatus());
            }
        }
        catch(IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Overloaded version of saveCards.
     * Saves card deck from specific file object to a text file.
     * @param file File that needs to be read in.
     * @return true if successful; false otherwise.
     */
    public boolean saveCards(File file) {
        try (PrintWriter out = new PrintWriter(
                               new BufferedWriter(
                               new FileWriter(file)))) {
            for (Card card : cards) {
                out.print(card.getQuestion() + FIELD_SEPERATOR);
                out.print(card.getAnswer() + FIELD_SEPERATOR);
                out.print(card.getInterval() + FIELD_SEPERATOR);
                out.println(card.getStatus());
            }
        }
        catch(IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * @param newCards Sets a new linked list of cards 
     * to current card deck.
     */
    public void setCards(LinkedList<Card> newCards) {
        cards = newCards;
    }
}
