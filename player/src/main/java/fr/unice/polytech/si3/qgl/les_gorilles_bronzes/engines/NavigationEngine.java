package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.Cockpit;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.under_engines.OarEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.under_engines.RudderEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.under_engines.SailEngine;
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
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities.VisibleEntity;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.OarConfiguration;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Sailor;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Ship;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Gouvernail;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Voile;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.pathfinding.Node;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.pathfinding.display.Display;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.util.Util;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static fr.unice.polytech.si3.qgl.les_gorilles_bronzes.util.Util.clamp;
import static fr.unice.polytech.si3.qgl.les_gorilles_bronzes.util.Util.clampAngle;

public class NavigationEngine {
    private boolean displayGraph;
    private InitGame initGame;
    private NextRound nextRound;
    private DeckEngine deckEngine;
    private int nextCheckpointToReach = 0;
    private Point nextPoint;
    private double nextPointRadius;
    private Point nextPoint2;

    private Display nodesDisplay;

    public NavigationEngine(InitGame initGame, DeckEngine deckEngine) {
        this.initGame = initGame;
        this.deckEngine = deckEngine;
        if (displayGraph) {
            nodesDisplay = Display.getInstance();
        }
    }

    public NavigationEngine(InitGame initGame, DeckEngine deckEngine, boolean displayGraph) {
        this(initGame, deckEngine);
        this.displayGraph = displayGraph;
        if (displayGraph) {
            nodesDisplay = Display.getInstance();
        }
    }

    public List<Action> computeNextRound(NextRound nextRound) {
        List<Action> actions = new ArrayList<>();
        this.nextRound = nextRound;

        actions.addAll(turnShipWithBestConfiguration());
        actions.addAll(new SailEngine(initGame, deckEngine, nextRound).addSailAction(new OarEngine(initGame, deckEngine, nextRound, nextCheckpointToReach).findBestConfiguration(getBestAngle(), getDistanceToCheckpoint(((RegattaGoal) initGame.getGoal()).getCheckpoints()[nextCheckpointToReach], nextRound.getShip())).getSpeed(), getGoalSpeed()));
        // TODO add vigie action

        return actions;
    }



    public List<Action> turnShipWithBestConfiguration() {
        List<Action> actions = new ArrayList<>();
        OarEngine oarEngine = new OarEngine(initGame, deckEngine, nextRound, nextCheckpointToReach);
        //oar action
        var checkpoint = ((RegattaGoal) initGame.getGoal()).getCheckpoints()[nextCheckpointToReach];
        actions.addAll(oarEngine.addOarAction(getBestAngle(), getDistanceToCheckpoint(checkpoint, nextRound.getShip())));
        //rudder action
        actions.addAll(new RudderEngine(deckEngine).addRudderActions(getBestAngle() - oarEngine.findBestConfiguration(getBestAngle(), getDistanceToCheckpoint(checkpoint, nextRound.getShip())).getAngle()));

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

    private boolean checkInTheWay(Point a, Point b) {
        VisibleEntity[] visibleEntities = nextRound.getVisibleEntities();
        if (visibleEntities != null) {
            return Arrays.stream(visibleEntities).anyMatch(e -> {
                if (e instanceof fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities.Stream) {
                    double angle = a.getAngleTo(b);
                    fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities.Stream stream = (fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities.Stream) e;

                    boolean condition = Math.cos(stream.getPosition().getOrientation() - angle) > 0;
                    stream.setShouldGoInto(condition);
                }
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
        List<Node> nodes = new ArrayList<>();

        Node ship = new Node(nextRound.getShip().getPosition());
        ship.g = 0;
        nodes.add(ship);

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
                for (Point point : visibleEntity.getShape().toPolygon().getPolygonWithMargin(50).getVertices()) {
                    nodes.add(new Node(visibleEntity.toGlobalCoordinates(point)));
                }
            }
        }

        var x = new LinkedList<Node>();
        x.add(ship);

        while (!x.isEmpty()) {
            var current = x.poll();

            for (Node node : nodes) {
                if (node == current) { // don't link a node with itself
                    continue;
                }

                var pos = node.getPoint();

                if (checkInTheWay(current.getPoint(), pos)) {
                    continue; // the node is not reachable
                }

                if (current.addBranch(node)) {
                    x.add(node);
                }
            }
        }

        var path = ship.findPathTo(checkpoint);

        if (path != null && path.size() > 1) {
            nextPoint = path.get(1);
            nextPoint2 = null;
            if (displayGraph) {
                nodesDisplay.paintTheseNodes(nodes);
            }
        } else {
            // TODO : try to go straight instead
            Cockpit.log("NO PATH FOUND");
        }
    }

    public void setNextCheckpointToReach(int nextCheckpointToReach) {
        this.nextCheckpointToReach = nextCheckpointToReach;
    }
}
