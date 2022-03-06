package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        assertEquals(regattaGoal, regattaGoal2);
        assertEquals(regattaGoal.hashCode(), regattaGoal2.hashCode());
    }

    @Test
    void toStringTest() {
        assertEquals("RegattaGoal{checkpoints=[Checkpoint{position=null, shape=null}]}", regattaGoal.toString());
    }
}
