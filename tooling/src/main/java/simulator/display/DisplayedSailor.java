package simulator.display;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Sailor;

import java.awt.*;

public class DisplayedSailor extends Sailor {
    private Color color;
    private boolean hasDoneAnAction;

    public DisplayedSailor(int id, int x, int y, String name, Color color) {
        super(id, x, y, name);
        this.color = color;
        this.hasDoneAnAction = false;
    }

    public Color getColor() {
        return color;
    }

    public boolean hasDoneAnAction() {
        return hasDoneAnAction;
    }

    public void setHasDoneAnAction(boolean hasDoneAnAction) {
        this.hasDoneAnAction = hasDoneAnAction;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
