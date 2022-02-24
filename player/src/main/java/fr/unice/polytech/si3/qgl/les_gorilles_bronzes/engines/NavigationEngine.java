package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.Action;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.Oar;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.NextRound;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.enums.Direction;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Position;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Circle;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.Checkpoint;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.RegattaGoal;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.OarConfiguration;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Ship;

import java.util.*;
import java.util.stream.Stream;

public class NavigationEngine {
    private InitGame initGame;
    private NextRound nextRound;
    private DeckEngine deckEngine;
    private int nextCheckpointToReach = 0;

    public NavigationEngine(InitGame initGame, DeckEngine deckEngine) {
        this.initGame = initGame;
        this.deckEngine = deckEngine;
    }

    public List<Action> computeNextRound(NextRound nextRound) {
        List<Action> actions = new ArrayList<>();
        this.nextRound = nextRound;

        actions.addAll(turnShipWithOars());

        return actions;
    }

    public List<Action> turnShipWithOars() {
        List<Action> actions = new ArrayList<>();

        double goalAngle = getGoalAngle();
        int nbOars = deckEngine.getTotalNbSailorsOnOars(); // for the next weeks we need to change this number
        List<OarConfiguration> possibleAngles = new ArrayList<>();

        for (int i = 0; i <= nbOars / 2; i++) {
            for (int j = 0; j <= nbOars / 2; j++) {
                possibleAngles.add(new OarConfiguration(i, j, nbOars));
            }
        }

        possibleAngles.remove(0); // removing 0 oars on each side

        OarConfiguration bestConf = possibleAngles.stream()//NOSONAR
                .sorted(Comparator.<OarConfiguration>comparingInt(conf -> conf.getLeftOar() + conf.getRightOar()).reversed())
                .min(Comparator.comparingDouble(conf -> Math.abs(conf.getAngle() - goalAngle)))
                .get();

        var leftOars = deckEngine.getOars(Direction.LEFT).stream().limit(bestConf.getLeftOar());// take N left oars
        var rightOars = deckEngine.getOars(Direction.RIGHT).stream().limit(bestConf.getRightOar());// take M right oars

        Stream.concat(leftOars, rightOars) // we take all oars we want to activate
                .map(oar -> deckEngine.getSailorByEntity(oar)) // for each oar, we try to get the sailor that's on it
                .flatMap(Optional::stream) // we keep only the oars that do have a sailor on them
                .forEach(sailor -> actions.add(new Oar(sailor.getId()))); // we add an Oar action associated to each matching sailor

        System.out.println(bestConf);
        System.out.println(actions);
        return actions;
    }

<<<<<<< HEAD
    public boolean isShipInsideCheckpoint(Position checkpointPosition, Position boatPosition, double radius) {
        double distance = Math.hypot(checkpointPosition.getX() - boatPosition.getX(), checkpointPosition.getY() - boatPosition.getY());
        return distance <= radius;
=======
    public boolean isShipInsideCheckpoint(Checkpoint checkPoint, Ship ship){
        double distance = Math.hypot(checkPoint.getPosition().getX() - ship.getPosition().getX(), checkPoint.getPosition().getY() - ship.getPosition().getY());
        return distance <= ((Circle) checkPoint.getShape()).getRadius();
>>>>>>> ef4458d75c980bb162ff48c46d082d7da5257c3e
    }

    private double getGoalAngle() {
        Checkpoint[] checkpoints = ((RegattaGoal) initGame.getGoal()).getCheckpoints();

        var boatPosition = nextRound.getShip().getPosition();
        var checkpointPosition = checkpoints[nextCheckpointToReach].getPosition();

        updateCheckPointToReach(checkpoints, nextRound.getShip());

        var res = Math.atan2(checkpointPosition.getY() - boatPosition.getY(), checkpointPosition.getX() - boatPosition.getX()) - boatPosition.getOrientation();

        // clamps the value between -2pi and 2pi
        while (res < -Math.PI)
            res += 2 * Math.PI;
        while (res > Math.PI)
            res -= 2 * Math.PI;
        return res;
    }

<<<<<<< HEAD
    public void updateCheckPointToReach(Checkpoint[] checkpoints, Position boatPosition, Position checkpointPosition) {
        if (isShipInsideCheckpoint(checkpointPosition, boatPosition, ((Circle) checkpoints[nextCheckpointToReach].getShape()).getRadius())) {
            for (int i = 0; i < checkpoints.length; i++) {
                if (i == nextCheckpointToReach) {
=======
    public void updateCheckPointToReach(Checkpoint[] checkpoints, Ship ship) {
        if(isShipInsideCheckpoint(checkpoints[nextCheckpointToReach], ship)){
            for(int i = 0; i < checkpoints.length; i++){
                if(i == nextCheckpointToReach){
>>>>>>> ef4458d75c980bb162ff48c46d082d7da5257c3e
                    nextCheckpointToReach++;
                    break;
                }
            }
        }
    }

    public int getNextCheckpointToReach() {
        return nextCheckpointToReach;
    }

}
