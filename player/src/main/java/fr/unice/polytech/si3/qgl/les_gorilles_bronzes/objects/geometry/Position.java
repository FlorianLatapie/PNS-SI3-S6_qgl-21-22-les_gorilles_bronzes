package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry;

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
    public String toString() {
        return "Position{" +
                "x=" + getX() +
                ", y=" + getY() +
                ", orientation=" + orientation +
                '}';
    }
}
