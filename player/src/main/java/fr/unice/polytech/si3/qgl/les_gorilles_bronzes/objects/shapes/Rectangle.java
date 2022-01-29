package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.shapes;

public class Rectangle implements Shape {
    private double width;
    private double height;
    private double orientation;

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
}
