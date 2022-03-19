package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Point;

import java.util.Objects;

public class Rectangle extends Shape {
    private double width;
    private double height;
    private double orientation;

    public Rectangle() {
    }

    public Rectangle(double width, double height, double orientation) {
        this.width = width;
        this.height = height;
        this.orientation = orientation;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getOrientation() {
        return orientation;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    @Override
    public Polygon toPolygon() {
        Polygon polygon = new Polygon();
        Point[] vertices = new Point[4];
        vertices[0] = new Point(-getHeight() / 2, -getWidth() / 2);
        vertices[1] = new Point(getHeight() / 2, -getWidth() / 2);
        vertices[2] = new Point(getHeight() / 2, getWidth() / 2);
        vertices[3] = new Point(-getHeight() / 2, getWidth() / 2);
        polygon.setVertices(vertices);
        polygon.setOrientation(orientation);
        return polygon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rectangle)) return false;
        Rectangle rectangle = (Rectangle) o;
        return Double.compare(rectangle.getWidth(), getWidth()) == 0 && Double.compare(rectangle.getHeight(), getHeight()) == 0 && Double.compare(rectangle.getOrientation(), getOrientation()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWidth(), getHeight(), getOrientation());
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "width=" + width +
                ", height=" + height +
                ", orientation=" + orientation +
                '}';
    }

    @Override
    public boolean intersects(Point a, Point b) {
        return toPolygon().intersects(a, b);
    }
}
