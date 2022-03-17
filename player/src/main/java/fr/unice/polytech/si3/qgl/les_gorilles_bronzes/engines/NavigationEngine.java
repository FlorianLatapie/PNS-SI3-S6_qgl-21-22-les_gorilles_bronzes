package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.NextRound;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.*;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.enums.Direction;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Circle;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Rectangle;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.Checkpoint;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.RegattaGoal;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.Wind;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.OarConfiguration;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Sailor;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Ship;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Gouvernail;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Voile;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.util.Util;

import java.util.*;
import java.util.stream.Collectors;
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
        actions.addAll(addSailAction());

        return actions;
    }

    public List<OarConfiguration> getPossibleAnglesWithOars() {
        int nbOars = deckEngine.getTotalNbSailorsOnOars(); // for the next weeks we need to change this number
        List<OarConfiguration> possibleAngles = new ArrayList<>();

        for (int i = 0; i <= nbOars / 2; i++) {
            for (int j = 0; j <= nbOars / 2; j++) {
                possibleAngles.add(new OarConfiguration(i, j, nbOars));
            }
        }
        return possibleAngles;
    }

    public List<Action> turnShipWithRudder(Double angle, Sailor sailorOnRudder) {
        List<Action> actions = new ArrayList<>();
        double angleToTurnWithRudder = clamp(angle, -Math.PI / 4, Math.PI / 4);
        actions.add(new Turn(sailorOnRudder.getId(), angleToTurnWithRudder));

        return actions;
    }

    public List<Action> turnShipWithBestConfiguration() { //TODO too many lines!
        List<Action> actions = new ArrayList<>();

        OarConfiguration bestConf = findBestConfiguration();

        //rudder action
        Gouvernail rudder = findRudder();
        Optional<Sailor> sailorOnRudder = findSailorOnRudder(rudder);
        sailorOnRudder.ifPresent(sailor -> actions.addAll(turnShipWithRudder(getBestAngle() - bestConf.getAngle(), sailor)));

        //oar action
        addOarAction(actions, bestConf);

        return actions;
    }


    /**
     * Lifts sail if the wind blows at the back of the boat
     */
    public boolean shouldLiftSail() {
        Wind wind = nextRound.getWind();
        double windOrientation = wind.getOrientation();
        double windStrength = wind.getStrength();

        double shipOrientation = nextRound.getShip().getPosition().getOrientation();
        double clampedShipOrientation = clampAngle(shipOrientation);

        if (getWindSpeedRelativeToShip(wind) + findBestConfiguration().getSpeed() >= getGoalSpeed()) return false;

        return Math.abs(clampedShipOrientation - windOrientation) < Math.toRadians(90);
    }

    public double getWindSpeedRelativeToShip(Wind wind) {
        int nbSail = deckEngine.getEntitiesByClass(new Voile()).size();
        double shipOrientation = nextRound.getShip().getPosition().getOrientation();
        double clampedShipOrientation = clampAngle(shipOrientation);

        return nbSail * wind.getStrength() * Math.cos(clampedShipOrientation - wind.getOrientation());
    }

    public Voile findSail() {// TODO: use an optional
        var searchForSail = deckEngine.getEntitiesByClass(new Voile());
        if (searchForSail.isEmpty()) return null;
        return (Voile) deckEngine.getEntitiesByClass(new Voile()).get(0);
    }

    public Optional<Sailor> findSailorOnSail(Voile sail) {
        if (sail == null) return Optional.empty();
        return deckEngine.getSailorByEntity(sail);
    }

    public List<Action> addSailAction() { //TODO too many lines!
        List<Action> actions = new ArrayList<>();

        Voile sail = findSail();
        Optional<Sailor> sailorOnSail = findSailorOnSail(sail);

        if (sailorOnSail.isPresent() && shouldLiftSail()) {
            actions.add(new LiftSail(sailorOnSail.get().getId()));
        }
        if (sailorOnSail.isPresent() && !(shouldLiftSail())) {
            actions.add(new LowerSail(sailorOnSail.get().getId()));
        }

        return actions;
    }

    public Checkpoint getCheckpoint() {
        return ((RegattaGoal) initGame.getGoal()).getCheckpoints()[nextCheckpointToReach];

    }

    public double getGoalSpeed() { //TODO : check this algorithm : the unit tests shown that the speed was not calculated correctly, it is now fixed
        Checkpoint checkpoint = getCheckpoint();
        return bestDistance(checkpoint, nextRound.getShip())
                // - ((Circle)checkpoint.getShape()).getRadius()
                + ((Rectangle) nextRound.getShip().getShape()).getHeight();
    }

    public OarConfiguration findBestConfiguration() { //TODO too many lines!
        double goalAngle = getGoalAngle();
        double bestGoalAngle = getBestAngle();
        var checkpoint = ((RegattaGoal) initGame.getGoal()).getCheckpoints()[nextCheckpointToReach];

        List<OarConfiguration> possibleAngles = getPossibleAnglesWithOars();
        List<OarConfiguration> bestConfs = possibleAngles.stream()
                .filter(conf -> willBeInsideCheckpoint(checkpoint, nextRound.getShip(), conf))
                .sorted(Comparator.<OarConfiguration>comparingInt(conf -> conf.getLeftOar() + conf.getRightOar()))
                .collect(Collectors.toList());

        OarConfiguration bestConf;

        if(bestConfs.isEmpty()){
            bestConf = possibleAngles.stream()//NOSONAR
                    .sorted(Comparator.<OarConfiguration>comparingInt(conf -> conf.getLeftOar() + conf.getRightOar()).reversed())
                    .min(Comparator.comparingDouble(conf -> Math.abs(conf.getAngle() - bestGoalAngle)))
                    .get();
        }
        else{
            bestConf = bestConfs.stream()
                    .min(Comparator.comparingDouble(conf -> Math.abs(conf.getAngle() - bestGoalAngle)))
                    .get();
        }

        return bestConf;
    }

    public Gouvernail findRudder() {
        var rechercheGouvernail = deckEngine.getEntitiesByClass(new Gouvernail());
        if (rechercheGouvernail.isEmpty()) return null;
        return (Gouvernail) deckEngine.getEntitiesByClass(new Gouvernail()).get(0);
    }

    public Optional<Sailor> findSailorOnRudder(Gouvernail rudder) {
        if (rudder == null) return Optional.empty();
        return deckEngine.getSailorByEntity(rudder);
    }

    public void addOarAction(List<Action> actions, OarConfiguration bestConf) {
        var leftOars = deckEngine.getOars(Direction.LEFT).stream().limit(bestConf.getLeftOar());// take N left oars
        var rightOars = deckEngine.getOars(Direction.RIGHT).stream().limit(bestConf.getRightOar());// take M right oars

        Stream.concat(leftOars, rightOars) // we take all oars we want to activate
                .map(oar -> deckEngine.getSailorByEntity(oar)) // for each oar, we try to get the sailor that's on it
                .flatMap(Optional::stream) // we keep only the oars that do have a sailor on them
                .forEach(sailor -> actions.add(new Oar(sailor.getId()))); // we add an Oar action associated to each matching sailor
    }

    public double bestDistance(Checkpoint checkPoint, Ship ship) {
        double checkX = checkPoint.getPosition().getX();
        double checkY = checkPoint.getPosition().getY();

        double shipX = ship.getPosition().getX();
        double shipY = ship.getPosition().getY();
        double shipOrientation = ship.getPosition().getOrientation();
        Rectangle shipShape = (Rectangle) ship.getShape();

        return Math.hypot(checkX - shipX + (Math.sin(shipOrientation) * (shipShape).getHeight() / 2),
                checkY - shipY + (Math.cos(shipOrientation) * (shipShape).getHeight() / 2));
    }

    public double getDistance(Checkpoint checkPoint, Ship ship){
        return Math.hypot(checkPoint.getPosition().getX() - (ship.getPosition().getX() +
                        (Math.sin(ship.getPosition().getOrientation()) * ((Rectangle)ship.getShape()).getHeight()/2))
                , checkPoint.getPosition().getY() - (ship.getPosition().getY() +
                        (Math.cos(ship.getPosition().getOrientation()) * ((Rectangle)ship.getShape()).getHeight()/2)));
    }

    public boolean isShipInsideCheckpoint(Checkpoint checkPoint, Ship ship){ //TODO liskov substitution
        return getDistance(checkPoint,ship) <= ((Circle) checkPoint.getShape()).getRadius();
    }

    public boolean willBeInsideCheckpoint(Checkpoint checkpoint, Ship ship, OarConfiguration oarConfiguration){ //TODO liskov substitution
        double distance = getDistance(checkpoint,ship)-oarConfiguration.getSpeed();
        return distance <= ((Circle) checkpoint.getShape()).getRadius();
    }

    private double getBestAngle(){ //TODO too many lines! / liskov substitution
        Checkpoint[] checkpoints = ((RegattaGoal) initGame.getGoal()).getCheckpoints();

        var boatPosition = nextRound.getShip().getPosition();

        updateCheckPointToReach(checkpoints, nextRound.getShip());

        var checkpointPosition = checkpoints[nextCheckpointToReach].getPosition();
        double res = getGoalAngle();

        if(checkpoints.length > nextCheckpointToReach+1){
            var nextCheckpointPosition = checkpoints[nextCheckpointToReach+1].getPosition();
            double angleNextCheckpoint = Math.atan2(nextCheckpointPosition.getY() - boatPosition.getY(), nextCheckpointPosition.getX() - boatPosition.getX()) - boatPosition.getOrientation();
            if(clampAngle(angleNextCheckpoint)<0 && clampAngle(angleNextCheckpoint)>=clampAngle(-5*Math.PI/6)){
                res = getGoalAngle() - (Math.atan2(((Circle)checkpoints[nextCheckpointToReach].getShape()).getRadius(), Math.hypot(checkpointPosition.getX() - nextRound.getShip().getPosition().getX()
                        , checkpointPosition.getY() - nextRound.getShip().getPosition().getY()))/1.5);
            }
            if(clampAngle(angleNextCheckpoint)>0 && clampAngle(angleNextCheckpoint)<=clampAngle(5*Math.PI/6)) {
                res = getGoalAngle() + (Math.atan2(((Circle) checkpoints[nextCheckpointToReach].getShape()).getRadius(), Math.hypot(checkpointPosition.getX() - nextRound.getShip().getPosition().getX()
                        , checkpointPosition.getY() - nextRound.getShip().getPosition().getY()))/1.5);
            }
        }
        return res;
    }

    public double getGoalAngle() { //TODO too many lines!
        Checkpoint[] checkpoints = ((RegattaGoal) initGame.getGoal()).getCheckpoints();

        var boatPosition = nextRound.getShip().getPosition();

        updateCheckPointToReach(checkpoints, nextRound.getShip());

        var checkpointPosition = checkpoints[nextCheckpointToReach].getPosition();

        var res = Math.atan2(checkpointPosition.getY() - boatPosition.getY(), checkpointPosition.getX() - boatPosition.getX()) - boatPosition.getOrientation();

        return Util.clampAngle(res);
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
