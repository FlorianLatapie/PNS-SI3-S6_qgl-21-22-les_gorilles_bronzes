package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeckTest {
    @Test
    void deckTest(){
        Deck deck = new Deck();
        deck.setWidth(10);
        deck.setLength(20);

        assertEquals(10, deck.getWidth());
        assertEquals(20, deck.getLength());
    }

    @Test
    void equalsTest(){
        Deck deck1 = new Deck();
        deck1.setWidth(10);
        deck1.setLength(20);

        Deck deck2 = new Deck();
        deck2.setWidth(10);
        deck2.setLength(20);

        assertEquals(deck1, deck2);
        assertEquals(deck1.hashCode(), deck2.hashCode());
    }
}
