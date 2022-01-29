package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity;

public class Voile implements Entity {
    private int x;
    private int y;
    private boolean openned;

    public boolean isOpenned() {
        return openned;
    }

    public void setOpenned(boolean openned) {
        this.openned = openned;
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
}
