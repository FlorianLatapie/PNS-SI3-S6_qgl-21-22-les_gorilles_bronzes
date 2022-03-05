package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = Void.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Rame.class, name = "oar"),
        @JsonSubTypes.Type(value = Voile.class, name = "sail"),
        @JsonSubTypes.Type(value = Gouvernail.class, name = "rudder"),
        @JsonSubTypes.Type(value = Vigie.class, name = "watch"),
        @JsonSubTypes.Type(value = Canon.class, name = "Canon")
})
public abstract class Entity {
    private int x;
    private int y;
    private boolean free;

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

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return x == entity.x && y == entity.y && free == entity.free;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, free);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +
                "(" + x +
                "," + y +
                ')';
    }
}
