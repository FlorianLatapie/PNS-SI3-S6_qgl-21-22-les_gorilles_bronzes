package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Move.class, name = "MOVING"),
        @JsonSubTypes.Type(value = LiftSail.class, name = "LIFT_SAIL"),
        @JsonSubTypes.Type(value = LowerSail.class, name = "LOWER_SAIL"),
        @JsonSubTypes.Type(value = Turn.class, name = "TURN"),
        @JsonSubTypes.Type(value = Oar.class, name = "OAR"),
        @JsonSubTypes.Type(value = UseWatch.class, name = "USE_WATCH"),
        @JsonSubTypes.Type(value = Aim.class, name = "AIM"),
        @JsonSubTypes.Type(value = Fire.class, name = "FIRE"),
        @JsonSubTypes.Type(value = Reload.class, name = "FIRE")
})
public abstract class Action {
    private int sailorId;

    protected Action(int sailorId) {
        this.sailorId = sailorId;
    }

    public int getSailorId() {
        return sailorId;
    }

    public void setSailorId(int sailorId) {
        this.sailorId = sailorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action action = (Action) o;
        return sailorId == action.sailorId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sailorId);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " sailorId=" + sailorId;

    }
}
