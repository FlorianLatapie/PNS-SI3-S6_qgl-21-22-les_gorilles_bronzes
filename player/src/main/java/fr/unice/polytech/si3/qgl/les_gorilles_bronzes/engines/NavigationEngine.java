package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.Actions.Action;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.Actions.Oar;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.NextRound;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Sailor;

import java.util.ArrayList;
import java.util.List;

public class NavigationEngine {
    private InitGame initGame;
    private NextRound nextRound;

    public NavigationEngine(InitGame initGame) {
        this.initGame = initGame;
    }

    public List<Action> computeNextRound(NextRound nextRound) {
        List<Action> actions = new ArrayList();


        for (Sailor sailor : initGame.getSailors()) {
            actions.add(new Oar(sailor.getId()));
        }

        return actions;
    }

    private boolean isCheckpointInFrontOfTheBoat() {
        return false;
    }
}
