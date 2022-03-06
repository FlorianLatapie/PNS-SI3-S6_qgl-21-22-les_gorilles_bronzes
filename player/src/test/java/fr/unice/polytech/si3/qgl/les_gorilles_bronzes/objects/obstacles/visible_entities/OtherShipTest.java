package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Position;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Circle;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OtherShipTest {
    OtherShip otherShip;
    Rectangle rectangle;

    @BeforeEach
    void setUp() {
        otherShip = new OtherShip();

        Position position = new Position(0,1,2);
        otherShip.setPosition(position);

        assertEquals(position, otherShip.getPosition());

        rectangle = new Rectangle();
        rectangle.setWidth(1);
        rectangle.setHeight(2);
        rectangle.setOrientation(3);
        otherShip.setShape(rectangle);
    }

    @Test
    void otherShipTest(){
        assertEquals(rectangle, otherShip.getShape());
    }

    @Test
    void equalsTest(){
        OtherShip otherShip2 = new OtherShip();
        otherShip2.setPosition(new Position(0,1,2));

        Rectangle rectangle2 = new Rectangle();
        rectangle2.setWidth(1);
        rectangle2.setHeight(2);
        rectangle2.setOrientation(3);
        otherShip2.setShape(rectangle2);
        assertEquals(otherShip2, otherShip);
        assertEquals(otherShip2.hashCode(), otherShip.hashCode());
    }
}
