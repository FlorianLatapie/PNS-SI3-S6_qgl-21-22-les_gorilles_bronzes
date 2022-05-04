package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PointTest {
    Point point;

    @BeforeEach
    void setUp() {
        point = new Point(1, 2);
    }

    @Test
    void pointTest() {
        assertEquals(1.0, point.getX());
        assertEquals(2.0, point.getY());

        point.setX(3.0);
        point.setY(4.0);

        assertEquals(3.0, point.getX());
        assertEquals(4.0, point.getY());

        assertEquals("Point{x=3.0, y=4.0}", point.toString());
    }

    @Test
    void equalsTest() {
        Point point2 = new Point(1, 2);
        Point point3 = new Point(3, 4);

        assertEquals(point, point);
        assertEquals(point.hashCode(), point.hashCode());
        assertEquals(point, point2);
        assertEquals(point.hashCode(), point2.hashCode());
        assertNotEquals(point, point3);
        assertNotEquals(point.hashCode(), point3.hashCode());
    }

    @Test
    void distanceToTest() {
        Point point2 = new Point(3, 4);
        assertEquals(2.8284271247461903, point.distanceTo(point2));

        point2 = new Point(1, 2);
        assertEquals(0, point.distanceTo(point2));
    }

    @Test
    void getAngleToTest() {
        Point point2 = new Point(3, 4);
        assertEquals(Math.PI / 4, point.getAngleTo(point2));

        point2 = new Point(1, 2);
        assertEquals(0, point.getAngleTo(point2));
    }

    @Test
    void normalizeTest() {
        Point point2 = new Point(4, 3);
        assertEquals(new Point(0.8, 0.6), point2.normalize());


        Point point3 = new Point(0, 0);
        assertEquals(new Point(0, 0), point3.normalize());
    }

    @Test
    void addTest() {
        Point point2 = new Point(3, 4);
        assertEquals(new Point(4, 6), point.add(point2));
    }

    @Test
    void multiplyTest() {
        assertEquals(new Point(2, 4), point.multiply(2));
    }

    @Test
    void dotProductTest() {
        Point point2 = new Point(3, 4);
        assertEquals(11, point.dotProduct(point2));
    }

    @Test
    void rotateByTest() {
        assertEquals(new Point(-2, 1), point.rotateBy(Math.PI / 2));
        assertEquals(new Point(2, -1), point.rotateBy(-Math.PI / 2));
        Point point2 = new Point(65465, 23856);
        assertEquals(new Point(61565.83460822776, -32625.495704929082), point2.rotateBy(46545));
    }

    @Test
    void crossProductTest() {
        Point point2 = new Point(3, 4);
        assertEquals(-2.0, point.crossProduct(point2));
    }

    @Test
    void fromPolarTest() {
        assertEquals(new Point(0, 0), Point.fromPolar(0, 0));
        assertEquals(new Point(0.8487048774164866, 1.321779532040728), Point.fromPolar(Math.PI / 2, 1));
    }

    @Test
    void distanceToLineTest() {
        assertEquals(0, Point.distanceToLine(new Point(1, 2), new Point(3, 4), new Point(5, 6)));
    }
}