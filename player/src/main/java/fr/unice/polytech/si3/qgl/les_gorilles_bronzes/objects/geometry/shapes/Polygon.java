package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Point;

import java.util.Arrays;
import java.util.Objects;

public class Polygon implements Shape{
    private	double orientation;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Polygon polygon = (Polygon) o;
        return Double.compare(polygon.orientation, orientation) == 0 && Arrays.equals(vertices, polygon.vertices);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(orientation);
        result = 31 * result + Arrays.hashCode(vertices);
        return result;
    }

    @Override
    public String toString() {
        return "Polygon{" +
                "orientation=" + orientation +
                ", vertices=" + Arrays.toString(vertices) +
                '}';
    }
}
