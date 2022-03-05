package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.Move;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Rame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SailorTest {
    @Test
    void sailorTest(){
        Sailor sailor = new Sailor(1, 0, 0, "Sailor");

        assertEquals(sailor, new Sailor(1, 0, 0, "Sailor"));
        assertEquals(sailor.hashCode(), new Sailor(1, 0, 0, "Sailor").hashCode());

        sailor.setId(2);
        assertEquals(2, sailor.getId());

        sailor.setX(1);
        assertEquals(1, sailor.getX());

        sailor.setY(1);
        assertEquals(1, sailor.getY());

        sailor.setName("Sailor2");
        assertEquals("Sailor2", sailor.getName());

        sailor.setFree(false);
        assertEquals(false, sailor.isFree());
    }

    @Test
    void toStringTest(){
        Sailor sailor = new Sailor(1, 0, 0, "Sailor");
        assertEquals("Sailor{id=1, x=0, y=0, name='Sailor', isFree=true}", sailor.toString());
    }

    @Test
    void moveTest(){
        Sailor sailor = new Sailor(1, 0, 0, "Sailor");

        Rame rame = new Rame();

        rame.setX(1);
        rame.setY(1);
        assertEquals(new Move(sailor.getId(), 1, 1), sailor.moveTo(rame));

        rame.setX(10);
        rame.setY(10);
        assertEquals(new Move(sailor.getId(), 0, 5), sailor.moveTo(rame));

        sailor = new Sailor(1, 0, 0, "Sailor");

        rame.setX(-2);
        rame.setY(-4);
        assertEquals(new Move(sailor.getId(), -1, -4), sailor.moveTo(rame));
    }
}