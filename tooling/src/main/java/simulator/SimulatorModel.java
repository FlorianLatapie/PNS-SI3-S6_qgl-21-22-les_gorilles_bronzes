package simulator;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.NextRound;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.*;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Point;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.Wind;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities.VisibleEntity;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Deck;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.OarConfiguration;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Ship;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Rame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Voile;
import simulator.objects.DisplayedSailor;
import simulator.objects.SimulatorInfos;
import simulator.util.AWTUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static fr.unice.polytech.si3.qgl.les_gorilles_bronzes.util.Util.clampAngle;

public class SimulatorModel {
    private final SimulatorInfos simulatorInfos;

    private DisplayedSailor[] generatedSailors;

    private Random random;

    private VisibleEntity[] seaEntities;
    private Wind wind;

    private Ship ship;

    private final long oarCount;
    private int leftOars;
    private int rightOars;

    private final long sailCount;
    private int activeSails;

    private double rudderAngle;

    private boolean useWatch;

    public SimulatorModel(SimulatorInfos simulatorInfos) {
        this.random = new Random();

        this.simulatorInfos = simulatorInfos;

        this.seaEntities = simulatorInfos.getSeaEntities();
        this.wind = simulatorInfos.getWind();

        this.ship = simulatorInfos.getShip();

        this.oarCount = Arrays.stream(simulatorInfos.getShip().getEntities()).filter(Rame.class::isInstance).count();
        this.sailCount = Arrays.stream(simulatorInfos.getShip().getEntities()).filter(Voile.class::isInstance).count();
        initCounts();
    }

    private void initCounts() {
        this.leftOars = 0;
        this.rightOars = 0;

        this.activeSails = 0;

        this.rudderAngle = 0;

        this.useWatch = false;

    }

    public DisplayedSailor[] generateSailors(int minSailors, int maxSailors, Deck deck) {
        var deckWidth = deck.getWidth();
        var deckLength = deck.getLength();
        var nbSailors = random.nextInt(maxSailors - minSailors + 1) + minSailors;

        var sailors = new ArrayList<DisplayedSailor>();
        var x = 0;
        var y = 0;
        for (int id = 0; id < nbSailors; id++) {
            Color color = AWTUtil.getRandomColor(50, 256);

            sailors.add(new DisplayedSailor(id, x, y, "" + id, color));

            if (x < deckWidth) {
                x++;
            } else {
                x = 0;
                y++;
                assert y >= deckLength;
            }
        }
        this.generatedSailors = sailors.toArray(new DisplayedSailor[0]);
        return generatedSailors;
    }

    public NextRound generateNextRound() {
        var nextRound = new NextRound();

        nextRound.setShip(updateShip());

        nextRound.setWind(updateWind());

        nextRound.setVisibleEntities(updateSeaEntities());

        return nextRound;
    }

    private Ship updateShip() {
        return ship;
    }

    private Wind updateWind() {
        return wind;
    }

    private VisibleEntity[] updateSeaEntities() {
        System.out.println("updateSeaEntities() : give all entities to the cockpit");
        return seaEntities;
    }

    public void applyActions(Action[] actions) {
        initCounts();
        // sort actions by their class : Move, Oar, LiftSail, LowerSail, Turn, UseWatch, Aim, Reload, Fire
        var moveActions = new ArrayList<Move>();
        var otherActions = new ArrayList<Action>();
        for (var action : actions) {
            if (action instanceof Move) {
                moveActions.add((Move) action);
            } else {
                otherActions.add(action);
            }
        }

        for (var action : moveActions) {
            var sailor = generatedSailors[action.getSailorId()];
            sailor.setX(sailor.getX() + action.getXdistance());
            sailor.setY(sailor.getY() + action.getYdistance());
        }

        for (var action : otherActions) {
            if (action instanceof Oar) {
                countActiveOars((Oar) action);
            } else if (action instanceof LiftSail) {
                activeSails++;
            } else if (action instanceof LowerSail) {
                //activeSails --;
            } else if (action instanceof Turn) {
                var turn = (Turn) action;
                rudderAngle = turn.getRotation();
            } else if (action instanceof UseWatch) {
                useWatch = true;
            } else {
                System.out.println("applyActions() : not yet implemented action : " + action.getClass().getSimpleName());
            }
        }

        //displayCounts();

        moveShip();
    }

    private void moveShip() {
        var linearSpeed = getOarSpeed() + getSailSpeed();
        var rotation = rudderAngle + new OarConfiguration(leftOars, rightOars, (int) oarCount).getAngle();

        var newPoint = Point.fromPolar(linearSpeed, ship.getPosition().getOrientation());
        var newShipPosition = ship.getPosition().add(newPoint.getX(), newPoint.getY(), rotation);
        ship.setPosition(newShipPosition);
    }

    private double getOarSpeed() {
        return 165.0 * (leftOars + rightOars) / (double) oarCount;
    }

    private double getSailSpeed() {
        var shipOrientation = ship.getPosition().getOrientation();
        double clampedShipOrientation = clampAngle(shipOrientation);

        var windSpeed = wind.getStrength();

        return (activeSails / sailCount) * windSpeed * Math.cos(clampedShipOrientation - wind.getOrientation());
    }

    private void displayCounts() {
        System.out.println("OAR_COUNT : " + oarCount);
        System.out.println("leftOars : " + leftOars);
        System.out.println("rightOars : " + rightOars);
        System.out.println("activeSails : " + activeSails);
        System.out.println("rudderAngle : " + rudderAngle);
        System.out.println("useWatch : " + useWatch);
    }

    private void countActiveOars(Oar action) {
        var oar = action;
        var sailor = generatedSailors[oar.getSailorId()];
        if (sailor.getY() == 0) {
            leftOars++;
        } else {
            rightOars++;
        }
    }

    public DisplayedSailor[] getSailors() {
        return generatedSailors;
    }
}
