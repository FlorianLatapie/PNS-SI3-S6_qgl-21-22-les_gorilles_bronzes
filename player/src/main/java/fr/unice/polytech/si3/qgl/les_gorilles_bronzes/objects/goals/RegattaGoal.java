package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals;

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
}
