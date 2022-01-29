package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.Actions;

public class Move implements Action {
    private int sailorId;
    private int xdistance; //absolute(xdistance) + absolute(ydistance) <= 5
    private int ydistance; //absolute(xdistance) + absolute(ydistance) <= 5

    public int getSailorId() {
        return sailorId;
    }

    public void setSailorId(int sailorId) {
        this.sailorId = sailorId;
    }

    public int getXdistance() {
        return xdistance;
    }

    public void setXdistance(int xdistance) {
        this.xdistance = xdistance;
    }

    public int getYdistance() {
        return ydistance;
    }

    public void setYdistance(int ydistance) {
        this.ydistance = ydistance;
    }
}
