package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck();
        deck.setWidth(10);
        deck.setLength(20);
    }

    @Test
    void deckTest() {
        assertEquals(10, deck.getWidth());
        assertEquals(20, deck.getLength());
    }

    @Test
    void equalsTest() {
        Deck deck2 = new Deck();
        deck2.setWidth(10);
        deck2.setLength(20);

        assertTrue(deck.equals(deck));
        assertFalse(deck.equals(null));
        assertFalse(deck.equals(new Object()));

        assertEquals(deck, deck2);
        assertNotEquals(deck, new Deck());
        assertEquals(deck.hashCode(), deck2.hashCode());
        assertNotEquals(deck.hashCode(), new Deck().hashCode());
    }

    @Test
    void toStringTest() {
        assertEquals("Deck{width=10, length=20}", deck.toString());
    }
}
