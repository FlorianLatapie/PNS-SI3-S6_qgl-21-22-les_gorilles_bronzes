package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship;


import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Position;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Rectangle;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Shape;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Entity;

import java.util.Arrays;
import java.util.Objects;

public class Ship {
    private String type;
    private int life;
    private Position position;
    private String name;
    private Deck deck;
    private Entity[] entities;
    private Shape shape;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Entity[] getEntities() {
        return entities;
    }

    public void setEntities(Entity[] entities) {
        this.entities = entities;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public double getLargestSideSize() {
        if (shape instanceof Rectangle) {
            return Math.max(((Rectangle) this.getShape()).getHeight(), ((Rectangle) this.getShape()).getWidth());
        } else {
            return 50;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        return life == ship.life && Objects.equals(type, ship.type) && Objects.equals(position, ship.position) && Objects.equals(name, ship.name) && Objects.equals(deck, ship.deck) && Arrays.equals(entities, ship.entities) && Objects.equals(shape, ship.shape);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(type, life, position, name, deck, shape);
        result = 31 * result + Arrays.hashCode(entities);
        return result;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "type='" + type + '\'' +
                ", life=" + life +
                ", position=" + position +
                ", name='" + name + '\'' +
                ", deck=" + deck +
                ", entities=" + Arrays.toString(entities) +
                ", shape=" + shape +
                '}';
    }
}
