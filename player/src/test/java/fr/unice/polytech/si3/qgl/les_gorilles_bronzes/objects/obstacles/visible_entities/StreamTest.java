package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StreamTest {
    @Test
    void streamTest(){
        Stream stream = new Stream();
        stream.setStrength(10);
        assertEquals(10, stream.getStrength());
    }
}
