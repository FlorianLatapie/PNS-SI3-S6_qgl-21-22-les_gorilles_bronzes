package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Position;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StreamTest {
    Stream stream;

    @BeforeEach
    void setUp() {
        stream = new Stream();
        stream.setStrength(10);
        stream.setPosition(new Position());
        stream.setShape(new Rectangle());
    }

    @Test
    void streamTest() {
        assertEquals(10, stream.getStrength());
        assertFalse(stream.shouldGoInto());
    }

    @Test
    void equalsTest() {
        stream.setShouldGoInto(true);
        Stream stream2 = new Stream();
        stream2.setStrength(10);
        stream2.setPosition(new Position());
        stream2.setShape(new Rectangle());
        stream2.setShouldGoInto(true);

        assertEquals(stream, stream2);
        assertEquals(stream.hashCode(), stream2.hashCode());

        Stream stream3 = new Stream();
        stream3.setStrength(10);
        stream3.setPosition(new Position(1, 1, 1));
        stream3.setShape(new Rectangle());
        stream3.setShouldGoInto(true);

        assertTrue(stream.equals(stream));
        assertFalse(stream.equals(null));
        assertFalse(stream.equals(new Object()));

        assertNotEquals(stream, stream3);
        assertNotEquals(stream.hashCode(), stream3.hashCode());
    }
}
