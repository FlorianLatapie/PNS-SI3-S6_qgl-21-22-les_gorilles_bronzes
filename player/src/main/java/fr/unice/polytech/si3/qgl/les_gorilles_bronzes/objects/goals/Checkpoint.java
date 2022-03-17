package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.PositionnedShape;

import java.util.Objects;

public class Checkpoint extends PositionnedShape {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Checkpoint that = (Checkpoint) o;
        return Objects.equals(getPosition(), that.getPosition()) && Objects.equals(getShape(), that.getShape());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPosition(), getShape());
    }

    @Override
    public String toString() {
        return "Checkpoint{" +
                "position=" + getPosition() +
                ", shape=" + getShape() +
                '}';
    }
}