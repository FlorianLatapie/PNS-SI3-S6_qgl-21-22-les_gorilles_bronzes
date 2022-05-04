package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VisibleEntityTest {
    @Test
    void otherShipTest() {
        OtherShip otherShip = new OtherShip();
        otherShip.setLife(10);

        assertTrue(otherShip.equals(otherShip));
        assertFalse(otherShip.equals(null));

        OtherShip otherShip2 = new OtherShip();
        otherShip2.setLife(10);

        assertEquals(10, otherShip.getLife());
        assertNotEquals(5, otherShip.getLife());

        assertEquals(otherShip, otherShip2);
        assertNotEquals(otherShip, new OtherShip());
        assertEquals(otherShip.hashCode(), otherShip2.hashCode());
        assertNotEquals(otherShip.hashCode(), new OtherShip().hashCode());
    }

    @Test
    void shouldGoIntoTest() {
        assertFalse(new OtherShip().shouldGoInto());

        Stream stream = new Stream();
        assertFalse(stream.shouldGoInto());
        stream.setShouldGoInto(true);
        assertTrue(stream.shouldGoInto());
    }
}
