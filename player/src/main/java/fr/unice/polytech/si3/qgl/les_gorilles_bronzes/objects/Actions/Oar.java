package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.Actions;

public class Oar implements Action {
    private int sailorId;

    public Oar(int sailorId) {
        this.sailorId = sailorId;
    }

    public int getSailorId() {
        return sailorId;
    }

    public void setSailorId(int sailorId) {
        this.sailorId = sailorId;
    }
}
