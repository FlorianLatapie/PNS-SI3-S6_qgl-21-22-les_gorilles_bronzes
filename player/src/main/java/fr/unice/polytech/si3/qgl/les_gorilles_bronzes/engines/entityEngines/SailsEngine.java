package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.entityEngines;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.DeckEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.NavigationEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.NextRound;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.Action;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.LiftSail;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.LowerSail;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.Wind;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Sailor;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Entity;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Voile;

import java.util.ArrayList;
import java.util.List;

import static fr.unice.polytech.si3.qgl.les_gorilles_bronzes.util.Util.clampAngle;

public class SailsEngine {
    private DeckEngine deckEngine;
    private OarsEngine oarsEngine;
    private NavigationEngine navigationEngine;

    public SailsEngine(DeckEngine deckEngine, OarsEngine oarsEngine, NavigationEngine navigationEngine) {
        this.deckEngine = deckEngine;
        this.oarsEngine = oarsEngine;
        this.navigationEngine = navigationEngine;
    }


    /**
     * Lifts sail if the wind blows at the back of the boat
     *
     * @return true if the sail should be lifted
     */
    public boolean shouldLiftSail(NextRound nextRound) {
        Wind wind = nextRound.getWind();
        double windOrientation = wind.getOrientation();

        double shipOrientation = nextRound.getShip().getPosition().getOrientation();
        double clampedShipOrientation = clampAngle(shipOrientation);

        if (getWindSpeedRelativeToShip(nextRound) + oarsEngine.findBestOarConfiguration(nextRound).getSpeed() >= navigationEngine.getGoalSpeed())
            return false;

        return Math.abs(clampedShipOrientation - windOrientation) < Math.toRadians(90);
    }

    public double getWindSpeedRelativeToShip(NextRound nextRound) {
        Wind wind = nextRound.getWind();
        int nbSail = deckEngine.getEntitiesByClass(new Voile()).size();
        double shipOrientation = nextRound.getShip().getPosition().getOrientation();
        double clampedShipOrientation = clampAngle(shipOrientation);

        return nbSail * wind.getStrength() * Math.cos(clampedShipOrientation - wind.getOrientation());
    }

    public List<Voile> findSails() {
        List<Voile> voiles = new ArrayList<>();
        var searchForSail = deckEngine.getEntitiesByClass(new Voile());
        if (searchForSail.isEmpty()) return voiles;
        for (int i = 0; i < searchForSail.size(); i++) {
            voiles.add((Voile) deckEngine.getEntitiesByClass(new Voile()).get(i));
        }
        return voiles;
    }

    public List<Action> getActionOnSails(Boolean shouldLiftSailValue) {
        List<Action> actions = new ArrayList<>();

        List<Voile> sails = new ArrayList<>();
        sails.addAll(findSails());

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


}
