package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.Actions.Action;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.Actions.Oar;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.NextRound;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Sailor;

import java.util.ArrayList;
import java.util.List;

public class GlobalEngine {
    private InitGame initGame;
    private int nbSailors;
    private int round = 0;
    private DeckEngine deckEngine;
    private NavigationEngine navigationEngine;

    public GlobalEngine(InitGame initGame) {
        this.initGame = initGame;
        this.deckEngine = new DeckEngine(initGame);
        this.navigationEngine = new NavigationEngine(initGame, deckEngine);
        this.nbSailors = initGame.getSailors().length;
    }

    public Action[] computeNextRound(NextRound nextRound) {
        List<Action> actions = new ArrayList();
        this.deckEngine.beforeEachRound();

        if (round == 0) {
            actions.addAll(deckEngine.moveSailorsToOars(nbSailors / 2, DeckEngine.Direction.LEFT));
            actions.addAll(deckEngine.moveSailorsToOars(nbSailors / 2, DeckEngine.Direction.RIGHT));
        }
        actions.addAll(navigationEngine.computeNextRound(nextRound));

        round++;
        return actions.toArray(new Action[0]);
    }
}
