package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship;


import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Position;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Shape;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Entity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public String getEntityNameWithPosition(int x, int y){
        List<Entity> entity = Arrays.stream(getEntities()).collect(Collectors.toList())
                .stream().filter(e-> e.getX()==x && e.getY()==y).collect(Collectors.toList());
        if(!entity.isEmpty()){
            return entity.get(0).getName()+"[" + x + "," +y+ "]";
        }
        return "No entity [" + x + "," +y+ "]";
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
