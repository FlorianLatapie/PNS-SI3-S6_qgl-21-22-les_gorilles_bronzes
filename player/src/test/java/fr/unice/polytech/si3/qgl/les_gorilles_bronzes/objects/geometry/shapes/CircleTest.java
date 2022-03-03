package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CircleTest {
    @Test
    void circleTest() {
        Circle circle = new Circle();
        assertEquals(0.0, circle.getRadius());
        circle.setRadius(1.0);
        assertEquals(1.0, circle.getRadius());

        assertEquals("Circle{radius=1.0}", circle.toString());
    }
}
