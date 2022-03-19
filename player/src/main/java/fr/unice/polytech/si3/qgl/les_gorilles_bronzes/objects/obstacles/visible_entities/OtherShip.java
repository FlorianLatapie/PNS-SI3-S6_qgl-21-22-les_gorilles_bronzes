package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities;

import java.util.Objects;

public class OtherShip extends VisibleEntity{
    private int life;

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OtherShip otherShip = (OtherShip) o;
        return life == otherShip.life;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), life);
    }

    @Override
    public String toString() {
        return "OtherShip{" +
                "position=" + getPosition() +
                ", shape=" + getShape() +
                ", life=" + life +
                '}';
    }
}
