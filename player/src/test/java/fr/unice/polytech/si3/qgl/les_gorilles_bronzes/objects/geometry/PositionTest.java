package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.Math.PI;
import static org.junit.jupiter.api.Assertions.*;

class PositionTest {
    Position position;

    @BeforeEach
    void setUp() {
        position = new Position(1.0, 2.0, PI / 2);
    }

    @Test
    void positionTest() {
        assertEquals(PI / 2, position.getOrientation());

        position.setOrientation(PI);

        assertEquals(PI, position.getOrientation());

        assertEquals("Position{x=1.0, y=2.0, orientation=3.141592653589793}", position.toString());
    }

    @Test
    void equalsTest() {
        assertTrue(position.equals(position));
        assertFalse(position.equals(null));
        assertFalse(position.equals(new Object()));

        assertEquals(position, new Position(1.0, 2.0, PI / 2));
        assertEquals(position.hashCode(), new Position(1.0, 2.0, PI / 2).hashCode());
        assertNotEquals(position, new Position(2.0, 2.0, PI / 2));
        assertNotEquals(position.hashCode(), new Position(2.0, 2.0, PI / 2).hashCode());
        assertNotEquals(position, new Position(1.0, 2.0, PI / 3));
        assertNotEquals(position.hashCode(), new Position(1.0, 2.0, PI / 3).hashCode());
    }

    @Test
    void addTest() {
        assertEquals(new Position(2.0, 4.0, 4.570796326794897), position.add(1, 2, 3));
    }
}