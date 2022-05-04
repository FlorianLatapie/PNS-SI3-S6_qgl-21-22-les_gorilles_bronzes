package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PolygonTest {
    Polygon polygon;

    @BeforeEach
    void setUp() {
        polygon = new Polygon();
        polygon.setVertices(new Point[]{new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(0, 1)});
        polygon.setOrientation(Math.PI / 2);
    }

    @Test
    void polygonTest() {
        Point[] vertices = new Point[]{new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(0, 1)};

        for (int i = 0; i < vertices.length; i++) {
            assertEquals(vertices[i], polygon.getVertices()[i]);
        }
        assertEquals(Math.PI / 2, polygon.getOrientation());

        assertEquals("Polygon{orientation=1.5707963267948966, vertices=[Point{x=0.0, y=0.0}, Point{x=1.0, y=0.0}, Point{x=1.0, y=1.0}, Point{x=0.0, y=1.0}]}", polygon.toString());
    }

    @Test
    void toStringTest() {
        assertEquals("Polygon{orientation=1.5707963267948966, vertices=[Point{x=0.0, y=0.0}, Point{x=1.0, y=0.0}, Point{x=1.0, y=1.0}, Point{x=0.0, y=1.0}]}", polygon.toString());
    }

    @Test
    void equalsTest() {
        Polygon polygon2 = new Polygon();
        polygon2.setOrientation(Math.PI / 2);
        polygon2.setVertices(new Point[]{new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(0, 1)});

        Polygon polygon3 = new Polygon();
        polygon3.setOrientation(Math.PI / 2);
        polygon3.setVertices(new Point[]{new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(0, 2)});

        assertEquals(polygon, polygon);
        assertEquals(polygon.hashCode(), polygon.hashCode());
        assertEquals(polygon, polygon2);
        assertEquals(polygon.hashCode(), polygon2.hashCode());
        assertNotEquals(polygon, polygon3);
        assertNotEquals(polygon.hashCode(), polygon3.hashCode());
    }

    @Test
    void getPolygonWithMarginTest() {
        Circle circle = new Circle();
        circle.setRadius(10);

        Polygon polygon = circle.toPolygon(3);
        Polygon polygonWithMargin = polygon.getPolygonWithMargin(5);

        assertEquals(3, polygon.getVertices().length);
        assertEquals(3, polygonWithMargin.getVertices().length);

        assertEquals(new Point(15, 0), polygonWithMargin.getVertices()[0]);
        assertEquals(new Point(-7.499999999999997, 12.99038105676658), polygonWithMargin.getVertices()[1]);
        assertEquals(new Point(-7.499999999999997, -12.99038105676658), polygonWithMargin.getVertices()[2]);
    }

    @Test
    void toPolygonTest() {
        assertEquals(polygon, polygon.toPolygon());
    }

    @Test
    void getCenterTest() {
        assertEquals(new Point(0.5, 0.5), polygon.getCenter());
    }

    @Test
    void intersectsTest(){
        assertFalse(polygon.intersects(new Point(0, 0), new Point(1, 0)));
        assertTrue(polygon.intersects(new Point(-5, -5), new Point(4, 5)));
    }
}
