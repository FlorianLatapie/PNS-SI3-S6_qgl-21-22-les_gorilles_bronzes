package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.Actions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

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
public interface Action {
}
