package flashcards;

/**
 * @author Zhishen (Jason) Wen and Eric Nida
 * @version Nov 29, 2012
 * MCIT, Penn CIS
 */
/****************************************************
 * Class that constructs basic Card object.
 ****************************************************
 */
public class Card {
    /**
     ******************** Fields **********************
     */
    private String question;
    private String answer;
    private int interval;
    private boolean isVirgin;
    
    /**
     ****************** Constructor *******************
     */
    public Card(String question, String answer, int interval, boolean isVirgin) {
        this.question = question;
        this.answer = answer;
        this.interval = interval;
        this.isVirgin = isVirgin;
    }
    
    /**
     ******************** Methods *********************
     */
    /**
     * @return Question of current card.
     */
    public String getQuestion() {
        return this.question;
    }
    
    /**
     * @return Answer of current card.
     */
    public String getAnswer() {
        return this.answer;
    }
    
    /**
     * @return Interval of current card.
     */
    public int getInterval() {
        return this.interval;
    }
    
    /**
     * @return true if current card is virgin;
     * false otherwise.
     */
    public boolean getStatus() {
        return this.isVirgin;
    }
    
    /**
     * @param question Set question to current card.
     */
    public void setQuestion(String question) {
        this.question = question;
    }
    
    /**
     * @param answer Set answer to current card.
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    /**
     * @param interval Set interval to current card.
     */
    public void setInterval(int interval) {
        this.interval = interval;
    }
    
    /**
     * @param isVirgin Set status to current card.
     */
    public void setStatus(boolean isVirgin) {
        this.isVirgin = isVirgin;
    }
    
    /**
     ************ Helpers for JUnit Test ************
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Card)) return false;
        Card that = (Card) o;
        if (this.getQuestion() != that.getQuestion() || 
                this.getAnswer() != that.getAnswer() ||
                this.getInterval() != that.getInterval() ||
                this.getStatus() != that.getStatus())
            return false;
        return true;
    }
    
}
