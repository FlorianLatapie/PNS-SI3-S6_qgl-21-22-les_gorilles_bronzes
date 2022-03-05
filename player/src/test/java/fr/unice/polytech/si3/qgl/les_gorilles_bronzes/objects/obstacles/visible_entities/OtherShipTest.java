package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Position;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Circle;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Rectangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OtherShipTest {
    @Test
    void otherShipTest(){
        OtherShip otherShip = new OtherShip();

        Position position = new Position(0,1,2);
        otherShip.setPosition(position);

        assertEquals(position, otherShip.getPosition());

        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(1);
        rectangle.setHeight(2);
        rectangle.setOrientation(3);
        otherShip.setShape(rectangle);

        assertEquals(rectangle, otherShip.getShape());
    }
}
