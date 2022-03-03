package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PolygonTest {
    @Test
    void polygonTest() {
        Polygon polygon = new Polygon();

        var points = new Point[]{new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(0, 1)};

        polygon.setVertices(points);
        polygon.setOrientation(Math.PI / 2);

        assertEquals(points, polygon.getVertices());
        assertEquals(Math.PI / 2, polygon.getOrientation());

        assertEquals("Polygon{orientation=1.5707963267948966, vertices=[Point{x=0.0, y=0.0}, Point{x=1.0, y=0.0}, Point{x=1.0, y=1.0}, Point{x=0.0, y=1.0}]}", polygon.toString());
    }
}
