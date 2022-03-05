package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Position;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Circle;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Shape;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Entity;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Rame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShipTest {
    @Test
    void shipTest(){
        Ship ship = new Ship();

        ship.setType("ship");
        ship.setLife(100);
        ship.setPosition(new Position(0,0, 0));
        ship.setName("ship");
        ship.setDeck(new Deck());
        ship.setEntities(new Entity[]{new Rame()});
        ship.setShape(new Circle());

        assertEquals("ship", ship.getType());
        assertEquals(100, ship.getLife());
        assertEquals(new Position(0,0, 0), ship.getPosition());
        assertEquals("ship", ship.getName());
        assertEquals(new Deck(), ship.getDeck());
        assertEquals(new Entity[]{new Rame()}[0], ship.getEntities()[0]);
        assertEquals(new Circle(), ship.getShape());

    }
}
