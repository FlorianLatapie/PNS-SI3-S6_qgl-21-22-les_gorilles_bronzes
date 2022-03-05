package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity;

import java.util.Objects;

public class Voile extends Entity {
    private boolean opened;

    public boolean isOpenned() {
        return opened;
    }

    public void setOpenned(boolean opened) {
        this.opened = opened;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Voile voile = (Voile) o;
        return opened == voile.opened;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), opened);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +
                "{" +
                "(" + getX() +
                "," + getY() +
                ')'  +
                ", opened=" + opened
                + "}";
    }
}
