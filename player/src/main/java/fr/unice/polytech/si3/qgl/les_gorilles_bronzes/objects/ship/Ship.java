package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship;


import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.Position;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.shapes.Shape;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Entity;

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
}
