package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.shapes;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = Void.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Circle.class, name = "circle"),
        @JsonSubTypes.Type(value = Polygon.class, name = "polygon"),
        @JsonSubTypes.Type(value = Rectangle.class, name = "rectangle")
})
public interface Shape {
}
