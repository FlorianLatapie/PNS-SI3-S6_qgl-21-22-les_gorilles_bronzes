package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegattaGoalTest {
    @Test
    void reggattaGoalTest() {
        RegattaGoal regattaGoal = new RegattaGoal();
        regattaGoal.setCheckpoints(new Checkpoint[]{new Checkpoint()});

        assertEquals(new Checkpoint[]{new Checkpoint()}[0], regattaGoal.getCheckpoints()[0]);
    }

    @Test
    void equalsTest() {
        RegattaGoal regattaGoal = new RegattaGoal();
        regattaGoal.setCheckpoints(new Checkpoint[]{new Checkpoint()});

        RegattaGoal regattaGoal2 = new RegattaGoal();
        regattaGoal2.setCheckpoints(new Checkpoint[]{new Checkpoint()});

        assertEquals(regattaGoal, regattaGoal2);
        assertEquals(regattaGoal.hashCode(), regattaGoal2.hashCode());
    }

    @Test
    void toStringTest() {
        RegattaGoal regattaGoal = new RegattaGoal();
        regattaGoal.setCheckpoints(new Checkpoint[]{new Checkpoint()});

        assertEquals("RegattaGoal{checkpoints=[Checkpoint{position=null, shape=null}]}", regattaGoal.toString());
    }
}
