package simulator;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.NextRound;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.*;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Position;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.Wind;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities.VisibleEntity;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Deck;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Sailor;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Ship;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Rame;
import simulator.objects.DisplayedSailor;
import simulator.objects.SimulatorInfos;
import simulator.util.AWTUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class SimulatorModel {
    private final SimulatorInfos simulatorInfos;

    private DisplayedSailor[] generatedSailors;

    private Position shipPos;
    private double shipSpeed;

    private long OAR_COUNT;
    private int leftOars;
    private int rightOars;

    private int activeSails;

    private double rudderAngle;

    private boolean useWatch;

    public SimulatorModel(SimulatorInfos simulatorInfos) {
        this.simulatorInfos = simulatorInfos;
        this.shipPos = simulatorInfos.getShip().getPosition();
        this.shipSpeed = 0;

        this.OAR_COUNT = Arrays.stream(simulatorInfos.getShip().getEntities()).filter(e -> e instanceof Rame).count();

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
        var nbSailors = new Random().nextInt(maxSailors - minSailors + 1) + minSailors;

        var sailors = new ArrayList<Sailor>();
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
        var ship = simulatorInfos.getShip();
        System.out.println("updateShip() : stub, returns the same ship !");
        return ship;
    }

    private Wind updateWind() {
        var wind = simulatorInfos.getWind();
        System.out.println("updateWind() : ok");
        return wind;
    }

    private VisibleEntity[] updateSeaEntities() {
        var seaEntities = simulatorInfos.getSeaEntities();
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
            var move = (Move) action;
            var sailor = generatedSailors[move.getSailorId()];
            sailor.setX(sailor.getX() + move.getXdistance());
            sailor.setY(sailor.getY() + move.getYdistance());
        }

        for (var action : otherActions) {
            if (action instanceof Oar) {
                countActiveOars((Oar) action);
            } else if (action instanceof LiftSail) {
                activeSails ++;
            } else if (action instanceof LowerSail) {
                activeSails --;
            } else if (action instanceof Turn) {
                var turn = (Turn) action;
                rudderAngle = turn.getRotation();
            } else if (action instanceof UseWatch) {
                useWatch = true;
            } else {
                System.out.println("applyActions() : not yet implemented action : " + action.getClass().getSimpleName());
            }
        }

        displayCounts();
    }

    private void displayCounts() {
        System.out.println("OAR_COUNT : " + OAR_COUNT);
        System.out.println("leftOars : " + leftOars);
        System.out.println("rightOars : " + rightOars);
        System.out.println("activeSails : " + activeSails);
        System.out.println("rudderAngle : " + rudderAngle);
        System.out.println("useWatch : " + useWatch);
    }

    private void countActiveOars(Oar action) {
        var oar = action;
        var sailor = generatedSailors[oar.getSailorId()];
        if (sailor.getY() == 0){
            leftOars ++;
        } else {
            rightOars ++;
        }
    }

    public DisplayedSailor[] getSailors() {
        return generatedSailors;
    }
}
