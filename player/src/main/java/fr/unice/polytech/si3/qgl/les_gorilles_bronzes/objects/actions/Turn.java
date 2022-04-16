package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions;

import java.util.Objects;

public class Turn extends Action {
    private double rotation;//-PI/4 <= rotation <= PI/4

    public Turn() {
    }

    public Turn(int sailorId, double rotation) {
        super(sailorId);
        this.rotation = rotation;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Turn turn = (Turn) o;
        return Double.compare(turn.rotation, rotation) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rotation);
    }

    @Override
    public String toString() {
        return super.toString() + "{" +
                "rotation=" + rotation +
                "}";
    }
}
