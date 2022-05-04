package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegattaGoalTest {
    RegattaGoal regattaGoal;

    @BeforeEach
    void setUp() {
        regattaGoal = new RegattaGoal();
        regattaGoal.setCheckpoints(new Checkpoint[]{new Checkpoint()});
    }

    @Test
    void reggattaGoalTest() {
        assertEquals(new Checkpoint[]{new Checkpoint()}[0], regattaGoal.getCheckpoints()[0]);
    }

    @Test
    void equalsTest() {
        RegattaGoal regattaGoal2 = new RegattaGoal();
        regattaGoal2.setCheckpoints(new Checkpoint[]{new Checkpoint()});

        assertTrue(regattaGoal.equals(regattaGoal));
        assertFalse(regattaGoal.equals(null));
        assertFalse(regattaGoal.equals(new Object()));

        assertEquals(regattaGoal, regattaGoal2);
        assertNotEquals(regattaGoal, new RegattaGoal());
        assertEquals(regattaGoal.hashCode(), regattaGoal2.hashCode());
        assertNotEquals(regattaGoal.hashCode(), new RegattaGoal().hashCode());
    }

    @Test
    void toStringTest() {
        assertEquals("RegattaGoal{checkpoints=[Checkpoint{position=null, shape=null}]}", regattaGoal.toString());
    }
}
