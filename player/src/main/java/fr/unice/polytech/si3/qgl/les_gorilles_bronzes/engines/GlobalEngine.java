package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.Actions.Action;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.NextRound;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.Checkpoint;

import java.util.ArrayList;
import java.util.List;

public class GlobalEngine {
    private Checkpoint checkpointToReach;
    private InitGame initGame;
    private int nbSailors;
    private int nbOars;
    private DeckEngine deckEngine;

    public GlobalEngine(InitGame initGame){
        this.initGame = initGame;
        this.deckEngine = new DeckEngine(initGame);
        this.nbSailors = initGame.getSailors().length;
        this.nbOars = deckEngine.getOars().size();
    }

    public Action[] computeNextRound(NextRound nextRound){
        List<Action> actions = new ArrayList();
        this.deckEngine.beforeEachRound();
        actions.addAll(deckEngine.moveSailorsToLeftOars(nbSailors/2));
        actions.addAll(deckEngine.moveSailorsToRightOars(nbSailors/2));
        return (Action[]) actions.toArray();
    }


}
