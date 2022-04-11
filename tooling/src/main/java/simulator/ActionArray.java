package simulator;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.Action;

import java.util.Arrays;

public class ActionArray {
    private Action[] actions;

    public ActionArray() {
    }

    public Action[] getActions() {
        return actions;
    }

    public void setActions(Action[] actions) {
        this.actions = actions;
    }

    @Override
    public String toString() {
        return "ActionArray{" +
                "actions=" + Arrays.toString(actions) +
                '}';
    }
}
