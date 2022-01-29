package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.Actions;

public class Turn implements Action {
    private int sailorId;
    private double rotation;//-PI/4 <= rotation <= PI/4

    public int getSailorId() {
        return sailorId;
    }

    public void setSailorId(int sailorId) {
        this.sailorId = sailorId;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
}
