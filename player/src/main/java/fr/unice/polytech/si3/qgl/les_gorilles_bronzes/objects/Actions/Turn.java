package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.Actions;

public class Turn extends Action {
    private double rotation;//-PI/4 <= rotation <= PI/4

    public Turn(int sailorId, double rotation) {
        super(sailorId);
        this.rotation = rotation;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
}
