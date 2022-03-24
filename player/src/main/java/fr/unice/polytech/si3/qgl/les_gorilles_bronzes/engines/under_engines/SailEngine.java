package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.under_engines;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.DeckEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.NavigationEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.NextRound;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.Action;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.LiftSail;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.LowerSail;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.Wind;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Sailor;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Voile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static fr.unice.polytech.si3.qgl.les_gorilles_bronzes.util.Util.clampAngle;

public class SailEngine {

    private InitGame initGame;
    private DeckEngine deckEngine;
    private NextRound nextRound;


    public SailEngine(InitGame initGame, DeckEngine deckEngine, NextRound nextRound) {
        this.initGame = initGame;
        this.deckEngine = deckEngine;
        this.nextRound = nextRound;
    }

    public List<Action> addSailAction(double speed, double goalspeed) {
        List<Action> actions = new ArrayList<>();

        Voile sail = findSail();
        Optional<Sailor> sailorOnSail = findSailorOnSail(sail);

        if (sailorOnSail.isPresent() && shouldLiftSail(speed, goalspeed)) {
            actions.add(new LiftSail(sailorOnSail.get().getId()));
        }
        if (sailorOnSail.isPresent() && !(shouldLiftSail(speed, goalspeed))) {
            actions.add(new LowerSail(sailorOnSail.get().getId()));
        }

        return actions;
    }

    /**
     * Lifts sail if the wind blows at the back of the boat
     */
    public boolean shouldLiftSail(double speed, double goalspeed) {
        Wind wind = nextRound.getWind();
        double windOrientation = wind.getOrientation();

        double shipOrientation = nextRound.getShip().getPosition().getOrientation();
        double clampedShipOrientation = clampAngle(shipOrientation);

        if (getWindSpeedRelativeToShip(wind) + speed >= goalspeed) return false;

        return Math.abs(clampedShipOrientation - windOrientation) < Math.toRadians(90);
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

    public double getWindSpeedRelativeToShip(Wind wind) {
        int nbSail = deckEngine.getEntitiesByClass(new Voile()).size();
        double shipOrientation = nextRound.getShip().getPosition().getOrientation();
        double clampedShipOrientation = clampAngle(shipOrientation);

        return nbSail * wind.getStrength() * Math.cos(clampedShipOrientation - wind.getOrientation());
    }
}
