package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Point;

import java.util.Arrays;
import java.util.Objects;

import static fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Point.distanceToLine;

public class Polygon implements Shape {
    private double orientation;
    private Point[] vertices;

    public double getOrientation() {
        return orientation;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    public Point[] getVertices() {
        return vertices;
    }

    public void setVertices(Point[] vertices) {
        this.vertices = vertices;
    }

    public Polygon getPolygonWithMargin(double margin) {
        var center = getCenter();
        Point[] verticesWithMargin = new Point[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            verticesWithMargin[i] = vertices[i].add(vertices[i].substract(center).normalize().multiply(margin));
        }
        Polygon result = new Polygon();
        result.setVertices(verticesWithMargin);
        result.orientation = orientation;
        return result;
    }

    public Point getCenter() {
        double x = 0;
        double y = 0;
        for (var vertex : vertices) {
            x += vertex.getX();
            y += vertex.getY();
        }
        return new Point(x / vertices.length, y / vertices.length);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Polygon)) return false;
        Polygon polygon = (Polygon) o;
        return Double.compare(polygon.getOrientation(), getOrientation()) == 0 && Arrays.equals(getVertices(), polygon.getVertices());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getOrientation());
        result = 31 * result + Arrays.hashCode(getVertices());
        return result;
    }

    @Override
    public String toString() {
        return "Polygon{" +
                "orientation=" + orientation +
                ", vertices=" + Arrays.toString(vertices) +
                '}';
    }

    @Override
    public Polygon toPolygon() {
        return this;
    }

    @Override
    public boolean intersects(Point p1, Point p2) {
        int j = vertices.length - 1;
        for (int i = 0; i < vertices.length; j = i++) {
            Point current = vertices[i];
            Point next = vertices[j];

            if (distanceToLine(current, p1, p2) * distanceToLine(next, p1, p2) < 0 &&
                    distanceToLine(current, next, p1) * distanceToLine(current, next, p2) < 0) {
                return true;
            }
        }

        return false;
    }
}
