package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.Cockpit;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.NextRound;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.Action;

import java.util.ArrayList;
import java.util.List;

public class GlobalEngine {
    private int round = 0;
    private final DeckEngine deckEngine;
    private final NavigationEngine navigationEngine;

    public GlobalEngine(InitGame initGame) {
        this.deckEngine = new DeckEngine(initGame);
        this.navigationEngine = new NavigationEngine(initGame, deckEngine);
    }

    public Action[] computeNextRound(NextRound nextRound) {
        List<Action> actions = new ArrayList<>();
        this.deckEngine.beforeEachRound();

        actions.addAll(deckEngine.placeSailors());

        actions.addAll(navigationEngine.computeNextRound(nextRound));

        round++;
        Cockpit.log(actions);
        return actions.toArray(new Action[0]);
    }

    public DeckEngine getDeckEngine() {
        return deckEngine;
    }

    public NavigationEngine getNavigationEngine() {
        return navigationEngine;
    }
}
