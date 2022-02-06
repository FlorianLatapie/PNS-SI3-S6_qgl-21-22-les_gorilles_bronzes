package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity;

public class Canon implements Entity{
    private int x;
    private int y;
    private boolean loaded;
    private double angle;

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName(){
        return "Canon";
    }
}
