package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Position;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Shape;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = Void.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = OtherShip.class, name = "ship"),
        @JsonSubTypes.Type(value = Stream.class, name = "stream"),
        @JsonSubTypes.Type(value = Reef.class, name = "reef")
})
public abstract class VisibleEntity {
    private Position position;
    private Shape shape;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }
}
