package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.Actions;

public class Aim implements Action {
    private int sailorId;
    private double angle;//-PI/4 <= rotation <= PI/4

    public int getSailorId() {
        return sailorId;
    }

    public void setSailorId(int sailorId) {
        this.sailorId = sailorId;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
