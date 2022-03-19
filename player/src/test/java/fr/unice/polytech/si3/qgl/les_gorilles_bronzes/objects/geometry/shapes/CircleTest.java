package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(circle, circle2);
        assertEquals(circle.hashCode(), circle2.hashCode());
    }

    @Test
    void getRadiusWithMarginTest() {
        circle.setRadius(1.0);
        assertEquals(11.0, circle.getRadiusWithMargin());

        circle.setMargin(0.5);
        assertEquals(1.5, circle.getRadiusWithMargin());
    }

    @Test
    void toPolygonTest() {
        int precision = 4;
        int margin = 5;

        circle.setMargin(margin);
        Polygon polygon = circle.toPolygon(precision);

        assertEquals(margin, polygon.getMargin());

        assertEquals(precision, polygon.getVertices().length);

        assertEquals(new Point(1, 0), polygon.getVertices()[0]);
        assertEquals(new Point(0, 1), polygon.getVertices()[1]);
        assertEquals(new Point(-1, 0), polygon.getVertices()[2]);
        assertEquals(new Point(0, -1), polygon.getVertices()[3]);
    }
}
