package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity;

import java.util.Objects;

public class Canon extends Entity{

    private boolean loaded;
    private double angle;

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
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
        Canon canon = (Canon) o;
        return loaded == canon.loaded && Double.compare(canon.angle, angle) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), loaded, angle);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +
                "{" +
                "(" + getX() +
                "," + getY() +
                ')'  +
                ", loaded=" + loaded +
                ", angle=" + angle +
                "}";
    }
}
