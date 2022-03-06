package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeckTest {
    Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck();
        deck.setWidth(10);
        deck.setLength(20);
    }

    @Test
    void deckTest(){
        assertEquals(10, deck.getWidth());
        assertEquals(20, deck.getLength());
    }

    @Test
    void equalsTest(){
        Deck deck2 = new Deck();
        deck2.setWidth(10);
        deck2.setLength(20);

        assertEquals(deck, deck2);
        assertEquals(deck.hashCode(), deck2.hashCode());
    }
}
