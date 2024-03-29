package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities;

import java.util.Objects;

public class Stream extends VisibleEntity {
    private double strength;

    public Stream() {
        this.setShouldGoInto(false);
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
        Stream stream = (Stream) o;
        return Double.compare(stream.strength, strength) == 0 && Objects.equals(getPosition(), stream.getPosition()) && Objects.equals(getShape(), stream.getShape());
    }

    @Override
    public int hashCode() {
        return Objects.hash(strength, getPosition(), getShape(), shouldGoInto());
    }


    @Override
    public String toString() {
        return "Stream{" +
                "position=" + getPosition() +
                ", shape=" + getShape() +
                ", strength=" + getStrength() +
                '}';
    }
}
