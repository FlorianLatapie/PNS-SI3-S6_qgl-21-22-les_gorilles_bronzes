package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void toStringTest(){
        assertEquals("Polygon{orientation=1.5707963267948966, vertices=[Point{x=0.0, y=0.0}, Point{x=1.0, y=0.0}, Point{x=1.0, y=1.0}, Point{x=0.0, y=1.0}]}", polygon.toString());
    }

    @Test
    void equalsTest(){
        Polygon polygon2 = new Polygon();
        polygon2.setOrientation(Math.PI / 2);
        polygon2.setVertices(new Point[]{new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(0, 1)});

        assertEquals(polygon, polygon2);
        assertEquals(polygon.hashCode(), polygon2.hashCode());
    }
}
