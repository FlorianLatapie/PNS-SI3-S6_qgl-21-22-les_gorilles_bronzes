package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = Void.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = OtherShip.class, name = "ship"),
        @JsonSubTypes.Type(value = Stream.class, name = "stream"),
        @JsonSubTypes.Type(value = Reef.class, name = "reef")
})
public interface VisibleEntity {
}
