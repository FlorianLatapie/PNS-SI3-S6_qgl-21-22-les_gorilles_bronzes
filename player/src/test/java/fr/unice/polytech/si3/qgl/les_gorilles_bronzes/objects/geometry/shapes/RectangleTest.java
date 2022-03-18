package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        assertEquals(rectangle, rectangle2);
        assertEquals(rectangle.hashCode(), rectangle2.hashCode());
    }

    @Test
    void getWidthWithMarginTest() {
        assertEquals(10, rectangle.getWidth());
        assertEquals(20, rectangle.getWidthWithMargin());
        rectangle.setMargin(5);
        assertEquals(15, rectangle.getWidthWithMargin());
    }

    @Test
    void getHeightWithMarginTest() {
        assertEquals(20, rectangle.getHeight());
        assertEquals(30, rectangle.getHeightWithMargin());
        rectangle.setMargin(5);
        assertEquals(25, rectangle.getHeightWithMargin());
    }

    @Test
    void toPolygonTest() {
        Polygon polygon = rectangle.toPolygon();
        assertEquals(4, polygon.getVertices().length);

        assertEquals(new Point(-5, -10), polygon.getVertices()[0]);
        assertEquals(new Point(5, -10), polygon.getVertices()[1]);
        assertEquals(new Point(5, 10), polygon.getVertices()[2]);
        assertEquals(new Point(-5, 10), polygon.getVertices()[3]);
    }
}
