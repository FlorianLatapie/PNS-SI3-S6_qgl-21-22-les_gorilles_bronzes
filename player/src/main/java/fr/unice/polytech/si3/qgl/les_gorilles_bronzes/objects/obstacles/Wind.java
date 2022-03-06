package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles;

import java.util.Objects;

public class Wind {
    private double orientation;
    private double strength;

    public double getOrientation() {
        return orientation;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wind wind = (Wind) o;
        return Double.compare(wind.orientation, orientation) == 0 && Double.compare(wind.strength, strength) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orientation, strength);
    }

    @Override
    public String toString() {
        return "Wind{" +
                "orientation=" + orientation +
                ", strength=" + strength +
                '}';
    }
}
