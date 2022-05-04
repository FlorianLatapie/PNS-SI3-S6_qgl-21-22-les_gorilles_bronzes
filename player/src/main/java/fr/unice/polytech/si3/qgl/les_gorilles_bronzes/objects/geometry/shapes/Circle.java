package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Point;

import java.util.Objects;

public class Circle implements Shape {
    private double radius;

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public Polygon toPolygon() {
        return toPolygon(20);
    }

    public Polygon toPolygon(int nbOfPoints) {
        Polygon polygon = new Polygon();
        Point[] points = new Point[nbOfPoints];
        double angle = 0;
        for (int i = 0; i < nbOfPoints; i++) {
            points[i] = new Point(radius * Math.cos(angle), radius * Math.sin(angle));
            angle += 2 * Math.PI / nbOfPoints;
        }
        polygon.setVertices(points);
        return polygon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Circle)) return false;
        Circle circle = (Circle) o;
        return Double.compare(circle.getRadius(), getRadius()) == 0;
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

    //https://www.desmos.com/calculator/qagzzrbcx1?lang=fr
    @Override
    public boolean intersects(Point e, Point l) {
        var rad = radius;
        var d = l.substract(e);

        // find intersection point between line and perpendicular line crossing the center
        double a = d.dotProduct(d);
        double b = 2 * e.dotProduct(d);
        double c = e.dotProduct(e) - rad * rad;

        double discriminant = b * b - 4 * a * c;

        // if line intersects then the point is inside the circle and discriminant is positive
        if (discriminant >= 0) {
            discriminant = Math.sqrt(discriminant);

            // t1 gives first intersection point
            double t1 = (-b - discriminant) / (2 * a);

            // if t1 is in [0,1] then intersection point is valid
            if (t1 >= 0 && t1 <= 1) {
                return true;
            }
            // t2 gives second intersection point
            double t2 = (-b + discriminant) / (2 * a);

            if (t2 >= 0 && t2 <= 1) {
                return true;
            }
            // if both intersection points are outside [0,1] then line is not intersecting
        }
        return false;
    }
}
