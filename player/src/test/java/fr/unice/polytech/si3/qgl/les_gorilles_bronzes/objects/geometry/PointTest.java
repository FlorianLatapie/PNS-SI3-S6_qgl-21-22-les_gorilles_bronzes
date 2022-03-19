package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.Cockpit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

        assertEquals(point, point2);
        assertNotEquals(point, point3);
    }

    @Test
    void distanceToTest(){
        Point point2 = new Point(3, 4);
        assertEquals(2.8284271247461903, point.distanceTo(point2));

        point2 = new Point(1, 2);
        assertEquals(0, point.distanceTo(point2));
    }

    @Test
    void getAngleToTest(){
        Point point2 = new Point(3, 4);
        assertEquals(Math.PI/4, point.getAngleTo(point2));

        point2 = new Point(1, 2);
        assertEquals(0, point.getAngleTo(point2));
    }

    @Test
    void normalizeTest(){
        Point point2 = new Point(4, 3);
        assertEquals(new Point(0.8,0.6), point2.normalize());
    }

    @Test
    void addTest(){
        Point point2 = new Point(3, 4);
        assertEquals(new Point(4, 6), point.add(point2));
    }

    @Test
    void multiplyTest(){
        assertEquals(new Point(2, 4), point.multiply(2));
    }
}