package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.Action;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.Oar;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.NextRound;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.Turn;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.enums.Direction;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Circle;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Rectangle;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.Checkpoint;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.RegattaGoal;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.OarConfiguration;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Sailor;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Entity;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Gouvernail;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.util.Util;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Ship;

import java.util.*;
import java.util.stream.Stream;

import static fr.unice.polytech.si3.qgl.les_gorilles_bronzes.util.Util.clamp;
import static fr.unice.polytech.si3.qgl.les_gorilles_bronzes.util.Util.clampAngle;

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

        actions.addAll(turnShipWithBestConfiguration());

        return actions;
    }

    public List<OarConfiguration> getPossibleAnglesWithOars(){
        int nbOars = deckEngine.getTotalNbSailorsOnOars(); // for the next weeks we need to change this number
        List<OarConfiguration> possibleAngles = new ArrayList<>();

        for (int i = 0; i <= nbOars / 2; i++) {
            for (int j = 0; j <= nbOars / 2; j++) {
                possibleAngles.add(new OarConfiguration(i, j, nbOars));
            }
        }
        return possibleAngles;
    }

    public List<Action> turnShipWithRudder(Double angle, Sailor sailorOnRudder, Entity rudderPosition){
        List<Action> actions = new ArrayList<>();
        double angleToTurnWithRudder = clamp(angle, -Math.PI / 4, Math.PI / 4);
        actions.add(new Turn(sailorOnRudder.getId(), angleToTurnWithRudder));

        return actions;
    }

    public List<Action> turnShipWithBestConfiguration(){
        List<Action> actions = new ArrayList<>();

        double goalAngle = getGoalAngle();
        List<OarConfiguration> possibleAngles = getPossibleAnglesWithOars();

        possibleAngles.remove(0); // removing 0 oars on each side

        var checkpoint = ((RegattaGoal) initGame.getGoal()).getCheckpoints()[nextCheckpointToReach];
        var objectif = bestDistance(checkpoint, nextRound.getShip())
                - ((Circle)checkpoint.getShape()).getRadius()
                + ((Rectangle)initGame.getShip().getShape()).getHeight();

        OarConfiguration bestConf = possibleAngles.stream()//NOSONAR
                .filter(conf -> conf.getSpeed() <= objectif)
                .sorted(Comparator.<OarConfiguration>comparingInt(conf -> conf.getLeftOar() + conf.getRightOar()).reversed())
                .min(Comparator.comparingDouble(conf -> Math.abs(conf.getAngle() - goalAngle)))
                .orElse(possibleAngles.get(0));

        var rechercheGouvernail = deckEngine.getEntitiesByClass(new Gouvernail());
        Optional<Sailor> sailorOnRudder = Optional.empty();
        Gouvernail rudder = null;

        if (!rechercheGouvernail.isEmpty()) {
            rudder = (Gouvernail) deckEngine.getEntitiesByClass(new Gouvernail()).get(0);
            sailorOnRudder = deckEngine.getSailorByEntity(rudder);
            if(sailorOnRudder.isPresent())
                actions.addAll(turnShipWithRudder(goalAngle-bestConf.getAngle(), sailorOnRudder.get(), rudder));
        }

        var leftOars = deckEngine.getOars(Direction.LEFT).stream().limit(bestConf.getLeftOar());// take N left oars
        var rightOars = deckEngine.getOars(Direction.RIGHT).stream().limit(bestConf.getRightOar());// take M right oars

        Stream.concat(leftOars, rightOars) // we take all oars we want to activate
                .map(oar -> deckEngine.getSailorByEntity(oar)) // for each oar, we try to get the sailor that's on it
                .flatMap(Optional::stream) // we keep only the oars that do have a sailor on them
                .forEach(sailor -> actions.add(new Oar(sailor.getId()))); // we add an Oar action associated to each matching sailor

        return actions;
    }

    public double bestDistance(Checkpoint checkPoint, Ship ship){
        return Math.hypot(checkPoint.getPosition().getX() - ship.getPosition().getX() +
                        (Math.sin(ship.getPosition().getOrientation()) * ((Rectangle)ship.getShape()).getHeight()/2),
                checkPoint.getPosition().getY() - ship.getPosition().getY() +
                        (Math.cos(ship.getPosition().getOrientation()) * ((Rectangle)ship.getShape()).getHeight()/2));
    }

    public boolean isShipInsideCheckpoint(Checkpoint checkPoint, Ship ship){
        double distance = Math.hypot(checkPoint.getPosition().getX() - ship.getPosition().getX() + (Math.sin(ship.getPosition().getOrientation()) * ((Rectangle)ship.getShape()).getHeight()/2)
                , checkPoint.getPosition().getY() - ship.getPosition().getY() + (Math.cos(ship.getPosition().getOrientation()) * ((Rectangle)ship.getShape()).getHeight()/2));
        return distance <= ((Circle) checkPoint.getShape()).getRadius();
    }

    private double getGoalAngle() {
        Checkpoint[] checkpoints = ((RegattaGoal) initGame.getGoal()).getCheckpoints();

        var boatPosition = nextRound.getShip().getPosition();

        updateCheckPointToReach(checkpoints, nextRound.getShip());

        var checkpointPosition = checkpoints[nextCheckpointToReach].getPosition();

        var res = Math.atan2(checkpointPosition.getY() - boatPosition.getY(), checkpointPosition.getX() - boatPosition.getX()) - boatPosition.getOrientation();

        // clamps the value between -2pi and 2pi
        while (res < -Math.PI)
            res += 2 * Math.PI;
        while (res > Math.PI)
            res -= 2 * Math.PI;
        return res;
    }

    public void updateCheckPointToReach(Checkpoint[] checkpoints, Ship ship) {
        if (isShipInsideCheckpoint(checkpoints[nextCheckpointToReach], ship)) {
            for (int i = 0; i < checkpoints.length; i++) {
                if (i == nextCheckpointToReach) {
                    if (checkpoints.length > nextCheckpointToReach + 1) {
                        nextCheckpointToReach++;
                    }
                    break;
                }
            }
        }
    }

    public int getNextCheckpointToReach() {
        return nextCheckpointToReach;
    }

}
