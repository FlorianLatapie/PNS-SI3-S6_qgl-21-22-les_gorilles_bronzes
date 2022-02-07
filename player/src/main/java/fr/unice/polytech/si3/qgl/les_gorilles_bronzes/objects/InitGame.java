package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.Goal;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.RegattaGoal;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Sailor;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Ship;

import java.util.Arrays;

public class InitGame {
    private Goal goal;
    private Ship ship;
    private Sailor[] sailors;
    private int shipCount;

    private RegattaGoal regattaGoal;

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

    public RegattaGoal getRegattaGoal() {
        return regattaGoal;
    }

    public void setRegattaGoal(RegattaGoal regattaGoal) {
        this.regattaGoal = regattaGoal;
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

    @Override
    public String toString() {
        return "InitGame{" + System.lineSeparator() +
                "goal=" + goal + System.lineSeparator() +
                ", ship=" + ship + System.lineSeparator() +
                ", sailors=" + Arrays.toString(sailors) + System.lineSeparator() +
                ", shipCount=" + shipCount + System.lineSeparator() +
                '}';
    }
}
