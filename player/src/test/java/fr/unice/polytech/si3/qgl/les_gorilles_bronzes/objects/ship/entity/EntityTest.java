package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {
    @Test
    void entityTest() {
        Rame rame = new Rame();
        rame.setX(0);
        rame.setY(1);
        rame.setFree(true);

        assertEquals(0, rame.getX());
        assertEquals(1, rame.getY());
        assertTrue(rame.isFree());

        Rame rame2 = new Rame();
        rame2.setX(0);
        rame2.setY(1);
        rame2.setFree(true);

        assertEquals(rame, rame2);
        assertEquals(rame.hashCode(), rame2.hashCode());
    }

    @Test
    void toStringTest() {
        Rame rame = new Rame();
        rame.setX(0);
        rame.setY(1);
        rame.setFree(true);

        assertEquals("Rame(0,1)", rame.toString());
    }

    @Test
    void voileTest() {
        Voile voile = new Voile();
        voile.setX(0);
        voile.setY(1);
        voile.setFree(true);
        voile.setOpenned(false);

        assertTrue(voile.equals(voile));
        assertFalse(voile.equals(null));

        assertFalse(voile.isOpenned());
        voile.setOpenned(true);
        assertTrue(voile.isOpenned());

        Voile voile2 = new Voile();
        voile2.setX(0);
        voile2.setY(1);
        voile2.setFree(true);
        voile2.setOpenned(true);

        Voile voile3 = new Voile();
        voile3.setX(0);
        voile3.setY(1);
        voile3.setFree(false);
        voile3.setOpenned(true);

        assertEquals(voile, voile2);
        assertEquals(voile.hashCode(), voile2.hashCode());
        assertNotEquals(voile, voile3);
        assertNotEquals(voile.hashCode(), voile3.hashCode());
        assertEquals(voile.hashCode(), voile2.hashCode());

        assertEquals("Voile{(0,1), opened=true}", voile.toString());
    }

    @Test
    void canonTest() {
        Canon canon = new Canon();
        canon.setX(0);
        canon.setY(1);
        canon.setFree(true);
        canon.setLoaded(false);
        canon.setAngle(10.0);

        assertTrue(canon.equals(canon));
        assertFalse(canon.equals(null));

        assertFalse(canon.isLoaded());

        canon.setLoaded(true);
        assertTrue(canon.isLoaded());

        assertEquals(10.0, canon.getAngle());

        Canon canon2 = new Canon();
        canon2.setX(0);
        canon2.setY(1);
        canon2.setFree(true);
        canon2.setLoaded(true);
        canon2.setAngle(10.0);

        Canon canon3 = new Canon();
        canon3.setX(0);
        canon3.setY(1);
        canon3.setFree(true);
        canon3.setLoaded(false);
        canon3.setAngle(11.0);

        assertEquals(canon, canon2);
        assertEquals(canon.hashCode(), canon2.hashCode());
        assertNotEquals(canon3, canon);
        assertNotEquals(canon3.hashCode(), canon.hashCode());

        assertEquals("Canon{(0,1), loaded=true, angle=10.0}", canon.toString());
    }
}
