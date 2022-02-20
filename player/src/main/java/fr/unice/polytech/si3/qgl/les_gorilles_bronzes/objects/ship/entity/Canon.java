package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity;

public class Canon extends Entity{

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
}
