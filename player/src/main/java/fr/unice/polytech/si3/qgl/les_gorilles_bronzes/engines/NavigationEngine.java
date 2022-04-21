package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.Cockpit;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.NextRound;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.*;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.enums.Direction;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Point;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Circle;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Rectangle;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.Checkpoint;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.RegattaGoal;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.Wind;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities.OtherShip;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities.VisibleEntity;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.OarConfiguration;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Sailor;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Ship;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Entity;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Gouvernail;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Vigie;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Voile;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.pathfinding.Node;
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
    private Point nextPoint;
    private double nextPointRadius;
    private Point nextPoint2;


    // cached values for performance
    private Set<VisibleEntity> visibleEntitiesCache;
    private double bestAngle;
    private boolean shouldLiftSailValue;
    private List<Point> path;
    private List<Node> nodes;

    public NavigationEngine(InitGame initGame, DeckEngine deckEngine) {
        this.initGame = initGame;
        this.deckEngine = deckEngine;

        visibleEntitiesCache = new HashSet<>();
    }

    public List<Action> computeNextRound(NextRound nextRound) {
        List<Action> actions = new ArrayList<>();
        this.nextRound = nextRound;
        bestAngle = getBestAngle();
        shouldLiftSailValue = shouldLiftSail();

        actions.addAll(turnShipWithBestConfiguration());
        actions.addAll(addSailAction());
        actions.addAll(addVigieAction());

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

    public List<Action> turnShipWithBestConfiguration() {
        List<Action> actions = new ArrayList<>();

        OarConfiguration bestConf = findBestOarConfiguration();

        //rudder action
        Gouvernail rudder = findRudder();
        Optional<Sailor> sailorOnRudder = findSailorOn(rudder);
        sailorOnRudder.ifPresent(sailor -> actions.addAll(turnShipWithRudder(bestAngle - bestConf.getAngle(), sailor)));

        //oar action
        addOarAction(actions, bestConf);

        return actions;
    }

    public Optional<Sailor> findSailorOn(Entity entity) {
        if (entity == null) return Optional.empty();
        return deckEngine.getSailorByEntity(entity);
    }


    /**
     * Lifts sail if the wind blows at the back of the boat
     */
    public boolean shouldLiftSail() {
        Wind wind = nextRound.getWind();
        double windOrientation = wind.getOrientation();

        double shipOrientation = nextRound.getShip().getPosition().getOrientation();
        double clampedShipOrientation = clampAngle(shipOrientation);

        if (getWindSpeedRelativeToShip(wind) + findBestOarConfiguration().getSpeed() >= getGoalSpeed()) return false;

        return Math.abs(clampedShipOrientation - windOrientation) < Math.toRadians(90);
    }

    public double getWindSpeedRelativeToShip(Wind wind) {
        int nbSail = deckEngine.getEntitiesByClass(new Voile()).size();
        double shipOrientation = nextRound.getShip().getPosition().getOrientation();
        double clampedShipOrientation = clampAngle(shipOrientation);

        return nbSail * wind.getStrength() * Math.cos(clampedShipOrientation - wind.getOrientation());
    }

    public List<Voile> findSail() {
        List<Voile> voiles = new ArrayList<>();
        var searchForSail = deckEngine.getEntitiesByClass(new Voile());
        if (searchForSail.isEmpty()) return voiles;
        for (int i = 0; i < searchForSail.size(); i++) {
            voiles.add((Voile) deckEngine.getEntitiesByClass(new Voile()).get(i));
        }
        return voiles;
    }

    public List<Action> addSailAction() {
        List<Action> actions = new ArrayList<>();

        List<Voile> sails = new ArrayList<>();
        sails.addAll(findSail());

        List<Sailor> sailorsOnSail = new ArrayList<>();

        for (Entity sail : sails) {
            deckEngine.getSailorByEntity(sail).ifPresent(sailorsOnSail::add);
        }

        for (Sailor sailorOnSail : sailorsOnSail) {
            if (shouldLiftSailValue) {
                actions.add(new LiftSail(sailorOnSail.getId()));
            }
            if (!(shouldLiftSailValue)) {
                actions.add(new LowerSail(sailorOnSail.getId()));
            }
        }

        return actions;
    }

    /**
     * Using vigie in any case
     */

    public Vigie findVigie() {// TODO: use an optional
        var searchForVigie = deckEngine.getEntitiesByClass(new Vigie());
        if (searchForVigie.isEmpty()) return null;
        return (Vigie) deckEngine.getEntitiesByClass(new Vigie()).get(0);
    }


    public List<Action> addVigieAction() {
        List<Action> actions = new ArrayList<>();

        Vigie vigie = findVigie();
        Optional<Sailor> sailorOnVigie = findSailorOn(vigie);

        if (sailorOnVigie.isPresent()) {
            actions.add(new UseWatch(sailorOnVigie.get().getId()));
        }

        return actions;
    }

    public Checkpoint getCheckpoint() {
        return ((RegattaGoal) initGame.getGoal()).getCheckpoints()[nextCheckpointToReach];

    }

    public double getGoalSpeed() {
        Checkpoint checkpoint = getCheckpoint();
        return bestDistance(checkpoint, nextRound.getShip())
                // - ((Circle)checkpoint.getShape()).getRadius()
                + ((Rectangle) nextRound.getShip().getShape()).getHeight();
    }

    public OarConfiguration findBestOarConfiguration() {
        double bestGoalAngle = bestAngle;
        var checkpoint = ((RegattaGoal) initGame.getGoal()).getCheckpoints()[nextCheckpointToReach];

        List<OarConfiguration> possibleAngles = getPossibleAnglesWithOars();
        List<OarConfiguration> bestConfs = possibleAngles.stream()
                .filter(conf -> willBeInsideCheckpoint(checkpoint, nextRound.getShip(), conf))
                .sorted(Comparator.<OarConfiguration>comparingInt(conf -> conf.getLeftOar() + conf.getRightOar()))
                .collect(Collectors.toList());

        OarConfiguration bestConf;

        if (bestConfs.isEmpty()) {
            bestConf = possibleAngles.stream()
                    .sorted(Comparator.<OarConfiguration>comparingInt(conf -> conf.getLeftOar() + conf.getRightOar()).reversed())
                    .min(Comparator.comparingDouble(conf -> Math.abs(conf.getAngle() - bestGoalAngle)))
                    .orElse(possibleAngles.get(0));
        } else {
            bestConf = bestConfs.stream()
                    .min(Comparator.comparingDouble(conf -> Math.abs(conf.getAngle() - bestGoalAngle)))
                    .orElse(bestConfs.get(0));
        }

        return bestConf;
    }

    public Gouvernail findRudder() {
        var rechercheGouvernail = deckEngine.getEntitiesByClass(new Gouvernail());
        if (rechercheGouvernail.isEmpty()) return null;
        return (Gouvernail) deckEngine.getEntitiesByClass(new Gouvernail()).get(0);
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

    public double getDistanceToCheckpoint(Checkpoint checkPoint, Ship ship) {
        double checkX = checkPoint.getPosition().getX();
        double checkY = checkPoint.getPosition().getY();

        double shipX = ship.getPosition().getX();
        double shipY = ship.getPosition().getY();
        double shipOrientation = ship.getPosition().getOrientation();


        return Math.hypot(checkX - (shipX + (Math.sin(shipOrientation) * ((Rectangle) ship.getShape()).getHeight() / 2)),
                checkY - (shipY + (Math.cos(shipOrientation) * ((Rectangle) ship.getShape()).getHeight() / 2)));
    }

    public boolean isShipInsideCheckpoint(Checkpoint checkPoint, Ship ship) {
        return getDistanceToCheckpoint(checkPoint, ship) <= ((Circle) checkPoint.getShape()).getRadius();
    }

    public boolean willBeInsideCheckpoint(Checkpoint checkpoint, Ship ship, OarConfiguration oarConfiguration) {
        double distance = getDistanceToCheckpoint(checkpoint, ship) - oarConfiguration.getSpeed();
        return distance <= ((Circle) checkpoint.getShape()).getRadius();
    }

    private double getBestAngle() {
        Checkpoint[] checkpoints = ((RegattaGoal) initGame.getGoal()).getCheckpoints();

        var boatPosition = nextRound.getShip().getPosition();

        updateCheckPointToReach(checkpoints, nextRound.getShip());

        var checkpointPosition = nextPoint;
        double res = getGoalAngle();

        if (nextPoint2 != null) {
            var nextCheckpointPosition = nextPoint2;
            double angleNextCheckpoint =
                    Math.atan2(nextCheckpointPosition.getY() - boatPosition.getY(), nextCheckpointPosition.getX() - boatPosition.getX()) - boatPosition.getOrientation();
            double delta = Math.atan2(nextPointRadius, Math.hypot(checkpointPosition.getX() - nextRound.getShip().getPosition().getX(),
                    checkpointPosition.getY() - nextRound.getShip().getPosition().getY())) / 1.5;
            double clamped = clampAngle(angleNextCheckpoint);
            if (clamped < 0 && clamped >= clampAngle(-5 * Math.PI / 6)) {
                res = getGoalAngle() - delta;
            } else if (clamped > 0 && clamped <= clampAngle(5 * Math.PI / 6)) {
                res = getGoalAngle() + delta;
            }
        }
        return res;
    }

    public double getGoalAngle() {
        Checkpoint[] checkpoints = ((RegattaGoal) initGame.getGoal()).getCheckpoints();

        var boatPosition = nextRound.getShip().getPosition();

        updateCheckPointToReach(checkpoints, nextRound.getShip());

        var checkpointPosition = nextPoint;

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
        nextPoint = checkpoints[nextCheckpointToReach].getPosition();
        if (nextCheckpointToReach + 1 < checkpoints.length) {
            nextPoint2 = checkpoints[nextCheckpointToReach + 1].getPosition();
            nextPointRadius = ((Circle) checkpoints[nextCheckpointToReach].getShape()).getRadius();
        } else {
            nextPoint2 = null;
        }

        // check whether the line (ship position ; nextPoint) intersects with something
        updateGraph();
    }

    private boolean checkInTheWay(Point a, Point b) {
        if (visibleEntitiesCache != null) {
            return visibleEntitiesCache.stream().anyMatch(e -> {
                return !e.shouldGoInto() && e.intersects(a, b);
            });
        } else {
            return true;
        }
    }

    public int getNextCheckpointToReach() {
        return nextCheckpointToReach;
    }

    public void updateGraph() {
        Checkpoint[] checkpoints = ((RegattaGoal) initGame.getGoal()).getCheckpoints();

        nodes = new ArrayList<>();
        nodes.clear();

        Ship ship = nextRound.getShip();
        Node shipNode = new Node(ship.getPosition());
        shipNode.g = 0;
        nodes.add(shipNode);

        Node checkpoint = new Node(checkpoints[nextCheckpointToReach].getPosition());
        nodes.add(checkpoint);

        try {
            Node nextCheckpoint = new Node(checkpoints[nextCheckpointToReach + 1].getPosition());
            nodes.add(nextCheckpoint);
        } catch (IndexOutOfBoundsException e) {
            // do nothing
        }

        VisibleEntity[] visibleEntities = nextRound.getVisibleEntities();

        if (visibleEntities != null) {

            for (VisibleEntity visibleEntity : visibleEntities) {
                if (!visibleEntitiesCache.contains(visibleEntity)) {
                    visibleEntity.setShape(visibleEntity.getShape().toPolygon().getPolygonWithMargin(50));
                    visibleEntitiesCache.add(visibleEntity);
                }
            }
            for (VisibleEntity visibleEntity : visibleEntities) {
                for (Point point : visibleEntity.getShape().toPolygon().getPolygonWithMargin(2).getVertices()) {
                    nodes.add(new Node(visibleEntity.toGlobalCoordinates(point)));
                }
            }
        }

        var x = new LinkedList<Node>();
        x.add(shipNode);

        while (!x.isEmpty()) {
            var current = x.poll();

            for (Node node : nodes) {
                if (node != current) { // don't link a node with itself
                    var pos = node.getPoint();

                    if (!checkInTheWay(current.getPoint(), pos) && current.addBranch(node)) {
                        x.add(node);
                    }
                }
            }
        }

        path = shipNode.findPathTo(checkpoint);

        if (path != null && path.size() > 1) {
            nextPoint = path.get(1);
            nextPoint2 = null;
        } else {
            Cockpit.log("NO PATH FOUND");
        }
        visibleEntitiesCache.removeIf(OtherShip.class::isInstance);
    }

    public List<Point> getPath() {
        return path;
    }

    public Set<VisibleEntity> getVisibleEntitiesCache() {
        return visibleEntitiesCache;
    }

    public List<Node> getNodes() {
        return nodes;
    }
}
