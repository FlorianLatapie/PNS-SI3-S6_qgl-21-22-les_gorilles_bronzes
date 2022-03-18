package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes;

import java.util.Objects;

public class Circle extends Shape {
    private double radius;
    private double margin = -1;

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getMargin() {
        if (margin == -1) {
            margin = DEFAULT_MARGIN;
        }
        return margin;
    }

    public void setMargin(double margin) {
        this.margin = margin;
    }

    public double getRadiusWithMargin() {
        return radius + getMargin();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Circle circle = (Circle) o;
        return Double.compare(circle.radius, radius) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(radius);
    }

    @Override
    public String toString() {
        return "Circle{" +
                "radius=" + radius +
                '}';
    }
}
