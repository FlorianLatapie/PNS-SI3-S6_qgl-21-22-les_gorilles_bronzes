package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RectangleTest {
    @Test
    void rectangleTest() {
        Rectangle rectangle = new Rectangle();

        rectangle.setWidth(10);
        rectangle.setHeight(20);
        rectangle.setOrientation(Math.PI / 2);

        assertEquals(10, rectangle.getWidth());
        assertEquals(20, rectangle.getHeight());
        assertEquals(Math.PI / 2, rectangle.getOrientation());

        assertEquals("Rectangle{width=10.0, height=20.0, orientation=1.5707963267948966}", rectangle.toString());
    }
}
