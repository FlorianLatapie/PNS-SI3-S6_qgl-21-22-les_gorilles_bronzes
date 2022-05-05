package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.Cockpit;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.entityEngines.OarsEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.entityEngines.RudderEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.entityEngines.SailsEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.entityEngines.VigieEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.NextRound;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.Action;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Point;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Circle;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Rectangle;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.Checkpoint;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.RegattaGoal;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities.OtherShip;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities.VisibleEntity;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.OarConfiguration;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Ship;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.pathfinding.Node;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.util.Util;

import java.util.*;

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
    private RudderEngine rudderEngine;
    private OarsEngine oarsEngine;
    private VigieEngine vigieEngine;
    private SailsEngine sailsEngine;

    public NavigationEngine(InitGame initGame, DeckEngine deckEngine) {
        this.initGame = initGame;
        this.deckEngine = deckEngine;
        this.rudderEngine = new RudderEngine(deckEngine);
        this.oarsEngine = new OarsEngine(deckEngine, this, initGame);
        this.vigieEngine = new VigieEngine(deckEngine);
        this.sailsEngine = new SailsEngine(deckEngine, oarsEngine, this);
        visibleEntitiesCache = new HashSet<>();
    }

    public List<Action> computeNextRound(NextRound nextRound) {
        List<Action> actions = new ArrayList<>();
        this.nextRound = nextRound;
        bestAngle = getBestAngle();
        shouldLiftSailValue = sailsEngine.shouldLiftSail(nextRound);

        actions.addAll(turnShipWithBestConfiguration());
        //sail action
        actions.addAll(sailsEngine.getActionOnSails(shouldLiftSailValue));
        //vigie action
        actions.addAll(vigieEngine.getActionOnVigie());

        return actions;
    }


    public List<Action> turnShipWithBestConfiguration() {
        List<Action> actions = new ArrayList<>();

        OarConfiguration bestConf = oarsEngine.findBestOarConfiguration(nextRound);

        //rudder action
        actions.addAll(rudderEngine.getActionOnRudder(getBestAngle(), bestConf.getAngle()));

        //oar action
        actions.addAll(oarsEngine.getActionOnOars(bestConf));

        return actions;
    }

    public double getGoalSpeed() {
        Checkpoint checkpoint = getCheckpoint();
        return bestDistance(checkpoint, nextRound.getShip())
                // - ((Circle)checkpoint.getShape()).getRadius()
                + ((Rectangle) nextRound.getShip().getShape()).getHeight();
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

    public Checkpoint getCheckpoint() {
        return ((RegattaGoal) initGame.getGoal()).getCheckpoints()[nextCheckpointToReach];

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


    public double getBestAngle() {
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

    public boolean willBeInsideCheckpoint(Checkpoint checkpoint, Ship ship, OarConfiguration oarConfiguration) {
        double distance = getDistanceToCheckpoint(checkpoint, ship) - oarConfiguration.getSpeed();
        return distance <= ((Circle) checkpoint.getShape()).getRadius();
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
                    visibleEntitiesCache.add(visibleEntity);
                }
            }
            for (VisibleEntity visibleEntity : visibleEntitiesCache) {
                for (Point point : visibleEntity.getShape().toPolygon().getPolygonWithMargin(50).getVertices()) {
                    nodes.add(new Node(visibleEntity.toGlobalCoordinates(point)));
                }
            }
        }

        if (visibleEntitiesCache != null) {
            for (VisibleEntity visibleEntity : visibleEntitiesCache) {
                if (visibleEntity instanceof fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities.Stream) {
                    boolean condition = Math.cos(visibleEntity.getPosition().getOrientation() - ship.getPosition().getOrientation()) > 0;
                    visibleEntity.setShouldGoInto(condition);
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

        if (path != null && !path.isEmpty()) {
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
