package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class VisibleEntityTest {
    @Test
    void otherShipTest(){
        OtherShip otherShip = new OtherShip();
        otherShip.setLife(10);

        OtherShip otherShip2 = new OtherShip();
        otherShip2.setLife(10);

        assertEquals(10, otherShip.getLife());
        assertNotEquals(5, otherShip.getLife());
        
        assertEquals(otherShip, otherShip2);
        assertNotEquals(otherShip, new OtherShip());
        assertEquals(otherShip.hashCode(), otherShip2.hashCode());
        assertNotEquals(otherShip.hashCode(), new OtherShip().hashCode());
    }
}
