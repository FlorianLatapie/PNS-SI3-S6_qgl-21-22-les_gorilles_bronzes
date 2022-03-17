package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes;

import java.util.Objects;

public class Rectangle implements Shape {
    private double width;
    private double height;
    private double orientation;
    private double margin = -1;

    public Rectangle (){
    }

    public Rectangle (double width, double height, double orientation){
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

    public double getMargin() {
        if (margin == -1) {
            margin = DEFAULT_MARGIN;
        }
        return margin;
    }

    public void setMargin(double margin) {
        this.margin = margin;
    }

    public double getHeightWithMargin() {
        return height + getMargin();
    }

    public double getWidthWithMargin(){
        return width + getMargin();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return Double.compare(rectangle.width, width) == 0 && Double.compare(rectangle.height, height) == 0 && Double.compare(rectangle.orientation, orientation) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height, orientation);
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "width=" + width +
                ", height=" + height +
                ", orientation=" + orientation +
                '}';
    }
}
