package flashcards;

import java.util.LinkedList;

import javax.swing.JOptionPane;

public class App {
    public static void main(String[] args) {
        
        FlashcardsIO io = new FlashcardsIO();
            LinkedList<Card> c = io.getCards();
            for (Card card : c) {
                System.out.println(card.getQuestion());
                System.out.println(card.getAnswer());
                System.out.println(card.getInterval());
                System.out.println(card.getStatus());
            }
            System.out.println("#####"+JOptionPane.YES_OPTION);
         
    /*
        LinkedList<Card> n = new LinkedList<>();
        n.add(new Card("NEW1", "NEWA1", 10, true));
        n.add(new Card("NEW1", "NEWA1", 10, true));
        n.add(new Card("NEW1", "NEWA1", 10, true));
        n.add(new Card("NEW1", "NEWA1", 10, true));
        io.setCards(n);
        io.saveCards();*/
        
        /*
        StudyModel s = new StudyModel();
        Card card1 = new Card("Question1", "Answer1", 5, true);
        Card card2 = new Card("Question2", "Answer2", 10, true);
        Card card3 = new Card("Question3", "Answer3", 20, false);
        Card card4 = new Card("Question4", "Answer4", 10, false);
        LinkedList<Card> cardDeck = new LinkedList<Card>();
        cardDeck.add(card1);
        cardDeck.add(card2);
        cardDeck.add(card3);
        cardDeck.add(card4);
        s.setCardDeck(cardDeck);*/
        
    }
}
