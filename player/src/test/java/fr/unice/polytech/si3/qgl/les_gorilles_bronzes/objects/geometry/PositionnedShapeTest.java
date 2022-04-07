package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Circle;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities.Reef;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionnedShapeTest {

    @Test
    void intersectsTest() {
        Reef reef = new Reef();
        Circle circle = new Circle();
        circle.setRadius(5);
        reef.setShape(circle);
        reef.setPosition(new Position(105, 0,0));
        assertTrue(reef.intersects(new Point(100, -10), new Point(100, 10)));
        assertFalse(reef.intersects(new Point(0, -10), new Point(0, -10)));
    }
}
