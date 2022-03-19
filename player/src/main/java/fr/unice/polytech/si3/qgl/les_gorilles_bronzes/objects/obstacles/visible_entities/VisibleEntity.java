package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.PositionnedShape;

import java.util.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = Void.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = OtherShip.class, name = "ship"),
        @JsonSubTypes.Type(value = Stream.class, name = "stream"),
        @JsonSubTypes.Type(value = Reef.class, name = "reef")
})
public abstract class VisibleEntity extends PositionnedShape {
    private boolean shouldGoInto = false;

    public boolean shouldGoInto() {
        return shouldGoInto;
    }

    public void setShouldGoInto(boolean shouldGoInto) {
        this.shouldGoInto = shouldGoInto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisibleEntity that = (VisibleEntity) o;
        return Objects.equals(getPosition(), that.getPosition()) && Objects.equals(getShape(), that.getShape());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPosition(), getShape());
    }

    @Override
    public String toString() {
        return "VisibleEntity{" +
                "position=" + getPosition() +
                ", shape=" + getShape() +
                '}';
    }
}
