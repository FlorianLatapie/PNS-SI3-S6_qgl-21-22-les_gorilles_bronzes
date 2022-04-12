package simulator;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.NextRound;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.Action;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.Move;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.Wind;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities.VisibleEntity;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Deck;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Sailor;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Ship;
import simulator.objects.DisplayedSailor;
import simulator.objects.SimulatorInfos;
import simulator.util.AWTUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class SimulatorModel {
    private SimulatorInfos simulatorInfos;

    private DisplayedSailor[] generatedSailors;

    public SimulatorModel(SimulatorInfos simulatorInfos) {
        this.simulatorInfos = simulatorInfos;
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
        System.out.println("updateSeaEntities() : display all entities");
        return seaEntities;
    }

    public void applyActions(Action[] actions) {
        for (var action : actions) {
            if (action instanceof Move) {
                var move = (Move) action;
                var sailor = generatedSailors[move.getSailorId()];
                sailor.setX(sailor.getX() + move.getXdistance());
                sailor.setY(sailor.getY() + move.getYdistance());
            }
        }
    }

    public DisplayedSailor[] getSailors() {
        return generatedSailors;
    }
}
