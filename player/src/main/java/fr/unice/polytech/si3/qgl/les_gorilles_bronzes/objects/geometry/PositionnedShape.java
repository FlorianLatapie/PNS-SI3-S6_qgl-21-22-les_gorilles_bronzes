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

    public boolean intersects(Point a, Point b) {
        // convert points to local coordinates
        a = a.substract(position).rotateBy(-position.getOrientation());
        b = b.substract(position).rotateBy(-position.getOrientation());

        return shape.intersects(a, b);
    }

    public Point toGlobalCoordinates(Point point) {
        return point.rotateBy(position.getOrientation()).add(position);
    }
}
