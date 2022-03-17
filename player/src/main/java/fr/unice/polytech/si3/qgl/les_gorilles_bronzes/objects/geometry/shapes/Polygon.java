package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Point;

import java.util.Arrays;
import java.util.Objects;

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

    public Rectangle toRectangle() {
        double leftMostValue = 0;
        double rightMostValue = 0;
        double topMostValue = 0;
        double bottomMostValue = 0;

        for (var vertex : vertices) {
            if (vertex.getX() < leftMostValue) {
                leftMostValue = vertex.getX();
            }   else if (vertex.getX() > rightMostValue) {
                rightMostValue = vertex.getX();
            }   else if (vertex.getY() < topMostValue) {
                topMostValue = vertex.getY();
            }   else if (vertex.getY() > bottomMostValue) {
                bottomMostValue = vertex.getY();
            }
        }

        var width = rightMostValue - leftMostValue;
        var height = bottomMostValue - topMostValue;

        System.out.println(Arrays.toString(vertices));
        System.out.println("leftMostValue: " + leftMostValue);
        System.out.println("rightMostValue: " + rightMostValue);
        System.out.println("topMostValue: " + topMostValue);
        System.out.println("bottomMostValue: " + bottomMostValue);
        System.out.println("width: " + width);
        System.out.println("height: " + height);

        return new Rectangle(width, height, orientation);
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
