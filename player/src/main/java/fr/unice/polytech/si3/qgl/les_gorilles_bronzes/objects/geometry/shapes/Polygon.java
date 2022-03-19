package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Point;

import java.util.Arrays;
import java.util.Objects;

public class Polygon extends Shape {
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

    public Rectangle toRectangle() {
        double leftMostValue = 0;
        double rightMostValue = 0;
        double topMostValue = 0;
        double bottomMostValue = 0;

        for (var vertex : vertices) {
            if (vertex.getX() < leftMostValue) {
                leftMostValue = vertex.getX();
            } else if (vertex.getX() > rightMostValue) {
                rightMostValue = vertex.getX();
            } else if (vertex.getY() < topMostValue) {
                topMostValue = vertex.getY();
            } else if (vertex.getY() > bottomMostValue) {
                bottomMostValue = vertex.getY();
            }
        }

        var width = rightMostValue - leftMostValue;
        var height = bottomMostValue - topMostValue;

        return new Rectangle(width, height, orientation);
    }

    public Polygon getPolygonWithMargin(double margin) {
        Point[] verticesWithMargin = new Point[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            verticesWithMargin[i] = vertices[i].add(vertices[i].normalize().multiply(margin));
        }
        Polygon result = new Polygon();
        result.setVertices(verticesWithMargin);
        result.orientation = orientation;
        return result;
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

    private static double distanceToLine(Point a, Point b, Point p) {
        var ba = b.substract(a);
        var pa = p.substract(a);
        return ba.crossProduct(pa);
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
