package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals;

import java.util.Arrays;

/**
 * @author Florian Latapie
 */
public class RegattaGoal implements Goal {
    private Checkpoint[] checkpoints;

    public Checkpoint[] getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(Checkpoint[] checkpoints) {
        this.checkpoints = checkpoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegattaGoal that = (RegattaGoal) o;
        return Arrays.equals(checkpoints, that.checkpoints);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(checkpoints);
    }

    @Override
    public String toString() {
        return "RegattaGoal{" +
                "checkpoints=" + Arrays.toString(checkpoints) +
                '}';
    }
}
