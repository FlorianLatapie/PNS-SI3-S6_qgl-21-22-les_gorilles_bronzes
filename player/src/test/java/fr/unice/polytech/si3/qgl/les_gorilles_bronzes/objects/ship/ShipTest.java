package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Position;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Circle;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Shape;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Entity;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Rame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShipTest {
    Ship ship;

    @BeforeEach
    void setUp() {
        ship = new Ship();

        ship.setType("ship");
        ship.setLife(100);
        ship.setPosition(new Position(0,0, 0));
        ship.setName("ship");
        ship.setDeck(new Deck());
        ship.setEntities(new Entity[]{new Rame()});
        ship.setShape(new Circle());
    }

    @Test
    void shipTest(){
        assertEquals("ship", ship.getType());
        assertEquals(100, ship.getLife());
        assertEquals(new Position(0,0, 0), ship.getPosition());
        assertEquals("ship", ship.getName());
        assertEquals(new Deck(), ship.getDeck());
        assertEquals(new Entity[]{new Rame()}[0], ship.getEntities()[0]);
        assertEquals(new Circle(), ship.getShape());
    }

    @Test
    void equalsTest(){
        Ship ship2 = new Ship();
        ship2.setType("ship");
        ship2.setLife(100);
        ship2.setPosition(new Position(0,0, 0));
        ship2.setName("ship");
        ship2.setDeck(new Deck());
        ship2.setEntities(new Entity[]{new Rame()});
        ship2.setShape(new Circle());

        assertEquals(ship, ship2);
        assertEquals(ship.hashCode(), ship2.hashCode());
    }
}
