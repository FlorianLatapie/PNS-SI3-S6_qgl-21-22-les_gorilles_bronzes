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
}
