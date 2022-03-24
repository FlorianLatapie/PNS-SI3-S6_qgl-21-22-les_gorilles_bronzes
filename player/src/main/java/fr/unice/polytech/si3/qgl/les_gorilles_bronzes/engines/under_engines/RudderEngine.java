package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.under_engines;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.DeckEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.Action;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.Turn;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Sailor;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Gouvernail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static fr.unice.polytech.si3.qgl.les_gorilles_bronzes.util.Util.clamp;

public class RudderEngine {

    private DeckEngine deckEngine;

    public RudderEngine(DeckEngine deckEngine){
        this.deckEngine = deckEngine;
    }

    public List<Action> addRudderActions(double angle){
        List<Action> actions = new ArrayList();
        Gouvernail rudder = findRudder();
        Optional<Sailor> sailorOnRudder = findSailorOnRudder(rudder);
        sailorOnRudder.ifPresent(sailor -> actions.addAll(turnShipWithRudder(angle, sailor)));
        return actions;
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

    public List<Action> turnShipWithRudder(Double angle, Sailor sailorOnRudder) {
        List<Action> actions = new ArrayList<>();
        double angleToTurnWithRudder = clamp(angle, -Math.PI / 4, Math.PI / 4);
        actions.add(new Turn(sailorOnRudder.getId(), angleToTurnWithRudder));

        return actions;
    }
}
