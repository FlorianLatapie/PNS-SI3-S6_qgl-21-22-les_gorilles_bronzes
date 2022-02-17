package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.Actions.Action;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.Actions.Oar;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.NextRound;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.RegattaGoal;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.OarConfiguration;

import java.util.*;
import java.util.stream.Stream;

public class NavigationEngine {
    private InitGame initGame;
    private NextRound nextRound;
    private DeckEngine deckEngine;
    private int nextCheckpointToReach = 0;

    public NavigationEngine(InitGame initGame, DeckEngine deckEngine) {
        this.initGame = initGame;
        this.deckEngine = deckEngine;
    }

    public List<Action> computeNextRound(NextRound nextRound) {
        List<Action> actions = new ArrayList();
        this.nextRound = nextRound;

        double goalAngle = getGoalAngle();
        int nbOars = initGame.getSailors().length; // for the next weeks we need to change this number
        List<OarConfiguration> possibleAngles = new ArrayList<>();
        for (int i = 0; i <= nbOars / 2; i++) {
            for (int j = 0; j <= nbOars / 2; j++) {
                possibleAngles.add(new OarConfiguration(i, j, nbOars));
            }
        }

        possibleAngles.remove(0); // removing 0 oars on each side

        OarConfiguration bestConf = possibleAngles.stream()//NOSONAR
                .sorted(Comparator.<OarConfiguration>comparingInt(conf -> conf.getLeftOar() + conf.getRightOar()).reversed())
                .min(Comparator.comparingDouble(conf -> Math.abs(conf.getAngle() - goalAngle)))
                .get();

        var leftOars = deckEngine.getLeftOars().stream().limit(bestConf.getLeftOar());// take N left oars
        var rightOars = deckEngine.getRightOars().stream().limit(bestConf.getRightOar());// take M right oars
        Stream.concat(leftOars, rightOars) // we take all oars we want to activate
                .map(oar -> deckEngine.getSailorByEntity(oar)) // for each oar, we try to get the sailor that's on it
                .flatMap(Optional::stream) // we keep only the oars that do have a sailor on them
                .forEach(sailor -> actions.add(new Oar(sailor.getId()))); // we add an Oar action associated to each matching sailor

        return actions;
    }

    private double getGoalAngle() {
        var boatPosition = nextRound.getShip().getPosition();
        var checkpointPosition = ((RegattaGoal) initGame.getGoal()).getCheckpoints()[nextCheckpointToReach].getPosition();
        var res = Math.atan2(checkpointPosition.getY() - boatPosition.getY(), checkpointPosition.getX() - boatPosition.getX()) - boatPosition.getOrientation();
        // clamps the value between -2pi and 2pi
        while (res < -Math.PI)
            res += 2 * Math.PI;
        while (res > Math.PI)
            res -= 2 * Math.PI;
        return res;
    }
}
