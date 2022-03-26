package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry;

import java.util.Objects;

public class Position extends Point{

    private double orientation;

    public Position (){
        super();
    }

    public Position(double x, double y, double orientation){
        super(x,y);
        this.orientation = orientation;
    }

    public double getOrientation() {
        return orientation;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Double.compare(position.orientation, orientation) == 0 && position.getX() == getX() && position.getY() == getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), orientation);
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + getX() +
                ", y=" + getY() +
                ", orientation=" + orientation +
                '}';
    }
}
