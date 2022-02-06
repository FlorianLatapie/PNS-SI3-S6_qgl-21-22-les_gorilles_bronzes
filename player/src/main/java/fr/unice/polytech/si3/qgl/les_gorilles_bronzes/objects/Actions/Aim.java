package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.Actions;

public class Aim extends Action {
    private double angle;//-PI/4 <= rotation <= PI/4

    public Aim(int sailorId, double angle) {
        super(sailorId);
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
