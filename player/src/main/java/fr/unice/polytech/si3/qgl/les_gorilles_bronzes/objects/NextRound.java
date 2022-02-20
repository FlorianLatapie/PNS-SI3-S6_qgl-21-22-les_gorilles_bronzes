package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities.VisibleEntity;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.Wind;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Ship;

public class NextRound {
    private Ship ship;
    private Wind wind;
    private VisibleEntity[] visibleEntities;

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public VisibleEntity[] getVisibleEntities() {
        return visibleEntities;
    }

    public void setVisibleEntities(VisibleEntity[] visibleEntities) {
        this.visibleEntities = visibleEntities;
    }
}
