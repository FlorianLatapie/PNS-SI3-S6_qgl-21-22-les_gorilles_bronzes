package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Position;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void streamTest(){
        assertEquals(10, stream.getStrength());
    }

    @Test
    void equalsTest(){
        Stream stream2 = new Stream();
        stream2.setStrength(10);
        stream2.setPosition(new Position());
        stream2.setShape(new Rectangle());

        assertEquals(stream, stream2);
        assertEquals(stream.hashCode(), stream2.hashCode());
    }
}
