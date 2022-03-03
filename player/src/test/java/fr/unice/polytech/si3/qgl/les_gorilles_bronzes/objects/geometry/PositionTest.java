package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry;

import org.junit.jupiter.api.Test;

import static java.lang.Math.PI;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PositionTest {
    @Test
    void initGameTest() {
        Position position = new Position(1.0, 2.0, PI / 2);

       assertEquals(PI/ 2, position.getOrientation());

        position.setOrientation(PI);

        assertEquals(PI, position.getOrientation());

        assertEquals("Position{x=1.0, y=2.0, orientation=3.141592653589793}", position.toString());
    }
}