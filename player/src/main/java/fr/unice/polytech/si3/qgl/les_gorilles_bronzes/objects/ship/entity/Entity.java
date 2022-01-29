package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = Void.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Rame.class, name = "oar"),
        @JsonSubTypes.Type(value = Voile.class, name = "sail"),
        @JsonSubTypes.Type(value = Gouvernail.class, name = "rudder"),
        @JsonSubTypes.Type(value = Vigie.class, name = "watch"),
        @JsonSubTypes.Type(value = Canon.class, name = "Canon")
})
public interface Entity {

}
