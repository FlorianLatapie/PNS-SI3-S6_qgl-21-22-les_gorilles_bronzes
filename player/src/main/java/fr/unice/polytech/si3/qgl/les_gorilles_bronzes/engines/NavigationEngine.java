package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.Actions.Action;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.Actions.Direction;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.Actions.Oar;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.NextRound;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.Checkpoint;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Sailor;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NavigationEngine {
    private InitGame initGame;
    private NextRound nextRound;
    private DeckEngine deckEngine;
    //private Checkpoint[] checkpoints;

    public NavigationEngine(InitGame initGame, DeckEngine deckEngine) {
        this.initGame = initGame;
        this.deckEngine = deckEngine;
        //this.checkpoints = initGame.getRegattaGoal().getCheckpoints();
    }

    public List<Action> turnShip(Direction direction){
        List<Sailor> rightSailors = new ArrayList<>();
        List<Sailor> leftSailors = new ArrayList<>();
        List<Action> actions = new ArrayList();

        if(direction == Direction.LEFT){
            leftSailors.addAll(deckEngine.getSailorsPerSide(Direction.LEFT));
            for (Sailor sailor : leftSailors) {
                actions.add(new Oar(sailor.getId()));
            }
        }
        if(direction == Direction.RIGHT){
            rightSailors.addAll(deckEngine.getSailorsPerSide(Direction.RIGHT));
            for (Sailor sailor : rightSailors) {
                actions.add(new Oar(sailor.getId()));
            }
        }

        return actions;
    }

    public List<Action> goStraight(){
        List<Action> actions = new ArrayList();

        for (Sailor sailor : initGame.getSailors()) {
            actions.add(new Oar(sailor.getId()));
        }

        return actions;
    }


    /*public void moveToCheckpointPosition(){
        Checkpoint nextCheckPoint = checkpoints[0];
        double x = nextCheckPoint.getPosition().getX();
        double y = nextCheckPoint.getPosition().getY();

        double myX = initGame.getShip().getPosition().getX();
        double myY = initGame.getShip().getPosition().getY();
    }*/

    public List<Action> computeNextRound(NextRound nextRound) {
        //return goStraight();
        return turnShip(Direction.RIGHT);
    }

    private boolean isCheckpointInFrontOfTheBoat() {
        return false;
    }
}
