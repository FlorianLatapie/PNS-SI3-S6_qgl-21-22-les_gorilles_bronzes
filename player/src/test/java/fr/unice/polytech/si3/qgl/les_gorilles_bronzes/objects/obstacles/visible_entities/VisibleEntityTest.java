package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VisibleEntityTest {
    @Test
    void otherShipTest(){
        OtherShip otherShip = new OtherShip();
        otherShip.setLife(10);
        assertEquals(10, otherShip.getLife());
    }


}
