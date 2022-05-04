package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CircleTest {
    Circle circle;

    @BeforeEach
    void setUp() {
        circle = new Circle();
        circle.setRadius(1.0);
    }

    @Test
    void circleTest() {
        circle.setRadius(1.0);
        assertEquals(1.0, circle.getRadius());

        assertEquals("Circle{radius=1.0}", circle.toString());
    }

    @Test
    void toStringTest() {
        assertEquals("Circle{radius=1.0}", circle.toString());
    }

    @Test
    void equalsTest() {
        Circle circle2 = new Circle();
        circle2.setRadius(1.0);

        Circle circle3 = new Circle();
        circle3.setRadius(2.0);

        assertEquals(circle, circle);
        assertEquals(circle.hashCode(), circle.hashCode());
        assertEquals(circle, circle2);
        assertEquals(circle.hashCode(), circle2.hashCode());
        assertNotEquals(circle, circle3);
        assertNotEquals(circle.hashCode(), circle3.hashCode());
    }

    @Test
    void toPolygonTest() {
        int precision = 4;

        Polygon polygon = circle.toPolygon(precision);

        assertEquals(precision, polygon.getVertices().length);

        assertEquals(new Point(1, 0), polygon.getVertices()[0]);
        assertEquals(new Point(0, 1), polygon.getVertices()[1]);
        assertEquals(new Point(-1, 0), polygon.getVertices()[2]);
        assertEquals(new Point(0, -1), polygon.getVertices()[3]);
    }

    @Test
    void toPolygon2Test() {
        int margin = 5;

        Polygon polygon = circle.toPolygon();

        assertEquals(20, polygon.getVertices().length);

        assertEquals(new Point(1, 0), polygon.getVertices()[0]);
    }

    @Test
    void intersectsTest() {
        assertTrue(circle.intersects(new Point(1, 0), new Point(10, 10)));
        assertFalse(circle.intersects(new Point(-4, 2), new Point(4, 2)));
        assertTrue(circle.intersects(new Point(0, 0), new Point(1, 1)));
        assertTrue(circle.intersects(new Point(1, 1), new Point(0, 0)));
        assertFalse(circle.intersects(new Point(2, 2), new Point(3, 3)));

    }
}
