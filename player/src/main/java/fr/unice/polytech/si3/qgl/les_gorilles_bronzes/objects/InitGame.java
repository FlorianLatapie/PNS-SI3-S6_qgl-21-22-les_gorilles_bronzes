package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.Goal;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Sailor;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Ship;

public class InitGame {
    private Goal goal;
    private Ship ship;
    private Sailor[] sailors;
    private int shipCount;

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public Sailor[] getSailors() {
        return sailors;
    }

    public void setSailors(Sailor[] sailors) {
        this.sailors = sailors;
    }

    public int getShipCount() {
        return shipCount;
    }

    public void setShipCount(int shipCount) {
        this.shipCount = shipCount;
    }
}
