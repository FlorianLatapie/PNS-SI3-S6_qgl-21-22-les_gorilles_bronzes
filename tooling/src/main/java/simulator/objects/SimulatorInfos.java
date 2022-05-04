package simulator.objects;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Position;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.Goal;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.Wind;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities.VisibleEntity;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Ship;

import java.util.Arrays;

public class SimulatorInfos {
    private Goal goal;
    private Ship ship;
    private Wind wind;
    private VisibleEntity[] seaEntities;
    private int maxRound;
    private int minumumCrewSize;
    private int maximumCrewSize;
    private Position[] startingPositions;


    public int getMaxRound() {
        return maxRound;
    }

    public void setMaxRound(int maxRound) {
        this.maxRound = maxRound;
    }

    public int getMinumumCrewSize() {
        return minumumCrewSize;
    }

    public void setMinumumCrewSize(int minumumCrewSize) {
        this.minumumCrewSize = minumumCrewSize;
    }

    public int getMaximumCrewSize() {
        return maximumCrewSize;
    }

    public void setMaximumCrewSize(int maximumCrewSize) {
        this.maximumCrewSize = maximumCrewSize;
    }

    public Position[] getStartingPositions() {
        return startingPositions;
    }

    public void setStartingPositions(Position[] startingPositions) {
        this.startingPositions = startingPositions;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public VisibleEntity[] getSeaEntities() {
        return seaEntities;
    }

    public void setSeaEntities(VisibleEntity[] seaEntities) {
        this.seaEntities = seaEntities;
    }

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

    @Override
    public String toString() {
        return "SimulatorInfos{" +
                "goal=" + goal +
                ", ship=" + ship +
                ", wind=" + wind +
                ", visibleEntities=" + Arrays.toString(seaEntities) +
                ", maxRound=" + maxRound +
                ", minumumCrewSize=" + minumumCrewSize +
                ", maximumCrewSize=" + maximumCrewSize +
                ", startingPositions=" + Arrays.toString(startingPositions) +
                '}';
    }
}
