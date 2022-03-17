package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Shape;

public abstract class PositionnedShape {
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
}
