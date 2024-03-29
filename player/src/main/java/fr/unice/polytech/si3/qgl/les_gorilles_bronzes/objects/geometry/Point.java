package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.util.Util;

import java.util.Objects;

public class Point {
    private double x;
    private double y;

    public Point() {
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Point fromPolar(double r, double theta) {
        return new Point(r * Math.cos(theta), r * Math.sin(theta));
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point point = (Point) o;
        return Util.areTheseDoubleAboutEqual(point.x, x) && Util.areTheseDoubleAboutEqual(point.y, y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Point{" + "x=" + x + ", y=" + y + '}';
    }

    public double distanceTo(Point destination) {
        return Math.hypot(destination.getX() - this.getX(), destination.getY() - this.getY());
    }

    public double getAngleTo(Point destination) {
        return Math.atan2(destination.getY() - this.getY(), destination.getX() - this.getX());
    }

    public Point normalize() {
        if (this.getX() == 0 && this.getY() == 0) {
            return new Point(0, 0);
        }
        double norm = Math.hypot(this.getX(), this.getY());
        return new Point(this.getX() / norm, this.getY() / norm);
    }

    public Point add(Point p) {
        return new Point(this.getX() + p.getX(), this.getY() + p.getY());
    }

    public Point substract(Point p) {
        return new Point(this.getX() - p.getX(), this.getY() - p.getY());
    }

    public Point multiply(double d) {
        return new Point(this.getX() * d, this.getY() * d);
    }

    public double dotProduct(Point p) {
        return this.getX() * p.getX() + this.getY() * p.getY();
    }

    public double crossProduct(Point other) {
        return this.getX() * other.getY() - this.getY() * other.getX();
    }

    public static double distanceToLine(Point a, Point b, Point p) {
        var ba = b.substract(a);
        var pa = p.substract(a);
        return ba.crossProduct(pa);
    }

    public Point rotateBy(double theta) {
        if (theta == 0) {
            return this;
        }
        double cos = Math.cos(theta);
        double sin = Math.sin(theta);
        return new Point(this.getX() * cos - this.getY() * sin, this.getX() * sin + this.getY() * cos);
    }
}
