package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.Math.PI;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PositionTest {
    Position position;

    @BeforeEach
    void setUp() {
        position = new Position(1.0, 2.0, PI / 2);
    }

    @Test
    void positionTest() {
        assertEquals(PI/ 2, position.getOrientation());

        position.setOrientation(PI);

        assertEquals(PI, position.getOrientation());

        assertEquals("Position{x=1.0, y=2.0, orientation=3.141592653589793}", position.toString());
    }

    @Test
    void equalsTest(){
        assertEquals(position, new Position(1.0, 2.0, PI / 2));
        assertEquals(position.hashCode(), new Position(1.0, 2.0, PI / 2).hashCode());
    }
}