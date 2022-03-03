package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.Move;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Entity;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.util.Util;

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

    public Move moveSailor(Entity entity) {
        int oldX = x;
        int oldY = y;

        int totalOfMovement = Math.abs(entity.getX() - getX()) + Math.abs(entity.getY() - getY());

        int entityX = entity.getX();
        int entityY = entity.getY();

        int rawDistanceX = entityX - oldX;
        int rawDistanceY = entityY - oldY;

        if (totalOfMovement <= 5) {
            setX(oldX + rawDistanceX);
            setY(oldY + rawDistanceY);

            return new Move(id, rawDistanceX, rawDistanceY);
        } else {
            int clampedDistanceY = Util.clamp(rawDistanceY, -5, 5);

            int xDistanceAvailable = 5 - Math.abs(clampedDistanceY);

            int xMove = Util.clamp(rawDistanceX, -xDistanceAvailable, xDistanceAvailable);
            int yMove = clampedDistanceY;

            setX(oldX + xMove);
            setY(oldY + yMove);
            return new Move(id, xMove, yMove);
        }
    }


    @Override
    public String toString() {
        return "Sailor{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                ", isFree=" + isFree +
                '}';
    }
}
