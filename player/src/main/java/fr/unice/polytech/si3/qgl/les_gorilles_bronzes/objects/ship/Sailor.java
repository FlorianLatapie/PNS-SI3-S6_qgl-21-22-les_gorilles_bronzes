package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.Actions.Move;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Entity;

public class Sailor {
    private int id;
    private int x;
    private int y;
    private String name;

    private boolean isFree;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public Move moveSailor(Entity entity){
        int totalOfMovement = Math.abs(entity.getX() - getX()) + Math.abs(entity.getY()- getY());
        if(totalOfMovement<=5) {
            setX(entity.getX());
            setY(entity.getY());
            return new Move(id, x, y);
        }else{
            System.out.println("Can't move "+ name +" to ["+ entity.getX() +" : "+ entity.getY() +"]");
            throw new RuntimeException("Can't move "+ name +" to ["+ entity.getX() +" : "+ entity.getY() +"]");
        }
    }
}
