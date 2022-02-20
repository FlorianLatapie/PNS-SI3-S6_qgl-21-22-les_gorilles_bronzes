package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions;

public class Move extends Action {
    private int xdistance; //absolute(xdistance) + absolute(ydistance) <= 5
    private int ydistance; //absolute(xdistance) + absolute(ydistance) <= 5

    public Move(int sailorId, int xdistance, int ydistance) {
        super(sailorId);
        this.xdistance = xdistance;
        this.ydistance = ydistance;
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

    @Override
    public String toString() {
        return "Move{" + super.toString() +
                "xdistance=" + xdistance +
                ", ydistance=" + ydistance +
                "} ";
    }
}
