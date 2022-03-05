package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes;

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
}
