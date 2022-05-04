package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {
    Rectangle rectangle;

    @BeforeEach
    void setUp() {
        rectangle = new Rectangle();

        rectangle.setWidth(10);
        rectangle.setHeight(20);
        rectangle.setOrientation(Math.PI / 2);
    }

    @Test
    void rectangleTest() {
        assertTrue(rectangle.equals(rectangle));
        assertFalse(rectangle.equals(null));
        assertFalse(rectangle.equals(new Object()));

        assertEquals(10, rectangle.getWidth());
        assertEquals(20, rectangle.getHeight());
        assertEquals(Math.PI / 2, rectangle.getOrientation());
    }

    @Test
    void toStringTest() {
        assertEquals("Rectangle{width=10.0, height=20.0, orientation=1.5707963267948966}", rectangle.toString());
    }

    @Test
    void equalsTest() {
        Rectangle rectangle2 = new Rectangle();
        rectangle2.setWidth(10);
        rectangle2.setHeight(20);
        rectangle2.setOrientation(Math.PI / 2);

        Rectangle rectangle3 = new Rectangle();
        rectangle3.setWidth(11);
        rectangle3.setHeight(21);
        rectangle3.setOrientation(Math.PI / 3);

        assertEquals(rectangle, rectangle);
        assertEquals(rectangle.hashCode(), rectangle.hashCode());
        assertEquals(rectangle, rectangle2);
        assertEquals(rectangle.hashCode(), rectangle2.hashCode());
        assertNotEquals(rectangle, rectangle3);
        assertNotEquals(rectangle.hashCode(), rectangle3.hashCode());
    }

    @Test
    void toPolygonTest() {
        Polygon polygon = rectangle.toPolygon();

        assertEquals(rectangle.getOrientation(), polygon.getOrientation());

        assertEquals(4, polygon.getVertices().length);
        ;

        assertEquals(new Point(-10, -5), polygon.getVertices()[0]);
        assertEquals(new Point(10, -5), polygon.getVertices()[1]);
        assertEquals(new Point(10, 5), polygon.getVertices()[2]);
        assertEquals(new Point(-10, 5), polygon.getVertices()[3]);
    }

    @Test
    void intersectsTest() {
        assertFalse(rectangle.intersects(new Point(0, 0), new Point(1, 0)));
        assertTrue(rectangle.intersects(new Point(-50, -50), new Point(40, 50)));
    }
}
