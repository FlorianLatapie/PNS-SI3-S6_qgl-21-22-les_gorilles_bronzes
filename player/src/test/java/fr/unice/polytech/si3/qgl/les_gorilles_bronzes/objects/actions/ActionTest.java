package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionTest {
    @Test
    void aimTest() {
        Aim aim = new Aim();
        aim.setSailorId(1);
        aim.setAngle(2.0);

        assertEquals(2.0, aim.getAngle());
        assertEquals(1, aim.getSailorId());
        aim.setAngle(3.0);
        assertEquals(3.0, aim.getAngle());
        aim.setSailorId(2);
        assertEquals(2, aim.getSailorId());
        assertEquals("Aim sailorId=2{angle=3.0}", aim.toString());

        assertTrue(aim.equals(aim));
        assertFalse(aim.equals(null));

        assertEquals(aim, new Aim(2, 3.0));
        assertEquals(aim.hashCode(), new Aim(2, 3.0).hashCode());
        assertNotEquals(aim, new Aim(2, 4.0));
        assertNotEquals(aim.hashCode(), new Aim(2, 4.0).hashCode());
    }

    @Test
    void FireTest() {
        Fire fire = new Fire(1);
        assertEquals(1, fire.getSailorId());
        assertEquals("Fire sailorId=1", fire.toString());

        assertEquals(fire, new Fire(1));
    }

    @Test
    void LiftSailTest() {
        LiftSail liftSail = new LiftSail(1);
        assertEquals(1, liftSail.getSailorId());
        assertEquals("LiftSail sailorId=1", liftSail.toString());

        assertEquals(liftSail, new LiftSail(1));
    }

    @Test
    void LowerSailTest() {
        LowerSail lowerSail = new LowerSail(1);
        assertEquals(1, lowerSail.getSailorId());
        assertEquals("LowerSail sailorId=1", lowerSail.toString());

        assertEquals(lowerSail, new LowerSail(1));
    }

    @Test
    void MoveTest() {
        Move move = new Move();
        move.setSailorId(1);
        move.setXdistance(2);
        move.setYdistance(3);
        assertEquals(2, move.getXdistance());
        assertEquals(3, move.getYdistance());

        move.setXdistance(4);
        move.setYdistance(5);

        assertEquals(4, move.getXdistance());
        assertEquals(5, move.getYdistance());
        assertEquals("Move sailorId=1{xdistance=4, ydistance=5}", move.toString());

        assertTrue(move.equals(move));
        assertFalse(move.equals(null));

        assertEquals(move, new Move(1, 4, 5));
        assertEquals(move.hashCode(), new Move(1, 4, 5).hashCode());
        assertNotEquals(move, new Move(1, 4, 6));
        assertNotEquals(move.hashCode(), new Move(1, 4, 6).hashCode());
    }

    @Test
    void OarTest() {
        Oar oar = new Oar(1);
        assertEquals(1, oar.getSailorId());
        assertEquals("Oar sailorId=1", oar.toString());

        assertEquals(oar, new Oar(1));
    }

    @Test
    void ReloadTest() {
        Reload reload = new Reload(1);
        assertEquals(1, reload.getSailorId());
        assertEquals("Reload sailorId=1", reload.toString());

        assertEquals(reload, new Reload(1));
    }

    @Test
    void TurnTest() {
        Turn turn = new Turn();
        turn.setSailorId(1);
        turn.setRotation(2.0);

        assertEquals(2, turn.getRotation());

        turn.setRotation(5.0);

        assertTrue(turn.equals(turn));
        assertFalse(turn.equals(null));

        assertEquals(5.0, turn.getRotation());
        assertEquals("Turn sailorId=1{rotation=5.0}", turn.toString());

        assertEquals(turn, new Turn(1, 5.0));
        assertEquals(turn.hashCode(), new Turn(1, 5.0).hashCode());
        assertNotEquals(turn, new Turn(1, 6.0));
        assertNotEquals(turn.hashCode(), new Turn(1, 6.0).hashCode());
    }

    @Test
    void UseWatchTest() {
        UseWatch useWatch = new UseWatch(1);
        assertEquals(1, useWatch.getSailorId());
        assertEquals("UseWatch sailorId=1", useWatch.toString());

        assertEquals(useWatch, new UseWatch(1));
    }
}