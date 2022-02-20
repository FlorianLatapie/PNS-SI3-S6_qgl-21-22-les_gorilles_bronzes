package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity;

public class Voile extends Entity {
    private boolean openned;

    public boolean isOpenned() {
        return openned;
    }

    public void setOpenned(boolean openned) {
        this.openned = openned;
    }
    @Override
    public String toString() {
        return super.toString().substring(0,super.toString().length()-1) +
                ", openned=" + openned + "} ";
    }
}
