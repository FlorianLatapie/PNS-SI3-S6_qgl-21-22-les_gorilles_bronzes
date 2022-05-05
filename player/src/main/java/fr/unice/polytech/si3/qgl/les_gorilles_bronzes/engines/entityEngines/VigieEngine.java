package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.entityEngines;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.DeckEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.Action;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.UseWatch;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Sailor;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Entity;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Vigie;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VigieEngine {
    DeckEngine deckEngine;

    public VigieEngine(DeckEngine deckEngine) {
        this.deckEngine = deckEngine;
    }

    public Optional<Vigie> findVigie() {
        var searchForVigie = deckEngine.getEntitiesByClass(new Vigie());
        if (searchForVigie.isEmpty()) return Optional.empty();
        return Optional.of((Vigie) deckEngine.getEntitiesByClass(new Vigie()).get(0));
    }

    public List<Action> getActionOnVigie() {
        List<Action> actions = new ArrayList<>();
        findVigie().flatMap(this::findSailorOn).ifPresent(sailor -> actions.add(new UseWatch(sailor.getId())));
        return actions;
    }

    public Optional<Sailor> findSailorOn(Entity entity) {
        if (entity == null) return Optional.empty();
        return deckEngine.getSailorByEntity(entity);
    }
}
