package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Position;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Shape;

import java.util.Objects;

public class Checkpoint{
    private Position position;
    private Shape shape;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Checkpoint that = (Checkpoint) o;
        return Objects.equals(position, that.position) && Objects.equals(shape, that.shape);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, shape);
    }

    @Override
    public String toString() {
        return "Checkpoint{" +
                "position=" + position +
                ", shape=" + shape +
                '}';
    }
}