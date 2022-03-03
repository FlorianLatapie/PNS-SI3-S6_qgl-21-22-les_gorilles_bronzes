package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.Cockpit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PointTest {
    @Test
    void initGameTest() {
        Point point = new Point(1.0, 2.0);

        assertEquals(1.0, point.getX());
        assertEquals(2.0, point.getY());

        point.setX(3.0);
        point.setY(4.0);

        assertEquals(3.0, point.getX());
        assertEquals(4.0, point.getY());

        assertEquals("Point{x=3.0, y=4.0}", point.toString());
    }
}