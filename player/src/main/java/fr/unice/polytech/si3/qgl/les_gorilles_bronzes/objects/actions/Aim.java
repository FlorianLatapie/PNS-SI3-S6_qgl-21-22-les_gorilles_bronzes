package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Aim aim = (Aim) o;
        return Double.compare(aim.angle, angle) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), angle);
    }

    @Override
    public String toString() {
        return super.toString() + "{" +
                "angle=" + angle +
                "}";
    }
}
