package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.entityEngines;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.DeckEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.NavigationEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.NextRound;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.Action;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.Oar;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.enums.Direction;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.RegattaGoal;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.OarConfiguration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OarsEngine {
    private DeckEngine deckEngine;
    private InitGame initGame;
    private NavigationEngine navigationEngine;

    public OarsEngine(DeckEngine deckEngine, NavigationEngine navigationEngine, InitGame initGame) {
        this.deckEngine = deckEngine;
        this.navigationEngine = navigationEngine;
        this.initGame = initGame;
    }

    public List<Action> getActionOnOars(OarConfiguration bestConf) {
        List<Action> actions = new ArrayList<>();
        var leftOars = deckEngine.getOars(Direction.LEFT).stream().limit(bestConf.getLeftOar());// take N left oars
        var rightOars = deckEngine.getOars(Direction.RIGHT).stream().limit(bestConf.getRightOar());// take M right oars

        Stream.concat(leftOars, rightOars) // we take all oars we want to activate
                .map(oar -> deckEngine.getSailorByEntity(oar)) // for each oar, we try to get the sailor that's on it
                .flatMap(Optional::stream) // we keep only the oars that do have a sailor on them
                .forEach(sailor -> actions.add(new Oar(sailor.getId()))); // we add an Oar action associated to each matching sailor
        return actions;
    }

    public List<OarConfiguration> getPossibleAnglesWithOars() {
        int nbOars = deckEngine.getTotalNbSailorsOnOars(); // for the next weeks we need to change this number
        List<OarConfiguration> possibleAngles = new ArrayList<>();

        for (int i = 0; i <= nbOars / 2; i++) {
            for (int j = 0; j <= nbOars / 2; j++) {
                possibleAngles.add(new OarConfiguration(i, j, nbOars));
            }
        }
        return possibleAngles;
    }

    public OarConfiguration findBestOarConfiguration(NextRound nextRound) {
        double bestGoalAngle = navigationEngine.getBestAngle();
        var checkpoint = ((RegattaGoal) initGame.getGoal()).getCheckpoints()[navigationEngine.getNextCheckpointToReach()];

        List<OarConfiguration> possibleAngles = getPossibleAnglesWithOars();
        List<OarConfiguration> bestConfs = possibleAngles.stream()
                .filter(conf -> navigationEngine.willBeInsideCheckpoint(checkpoint, nextRound.getShip(), conf))
                .sorted(Comparator.<OarConfiguration>comparingInt(conf -> conf.getLeftOar() + conf.getRightOar()))
                .collect(Collectors.toList());

        OarConfiguration bestConf;

        if (bestConfs.isEmpty()) {
            bestConf = possibleAngles.stream()
                    .sorted(Comparator.<OarConfiguration>comparingInt(conf -> conf.getLeftOar() + conf.getRightOar()).reversed())
                    .min(Comparator.comparingDouble(conf -> Math.abs(conf.getAngle() - bestGoalAngle)))
                    .orElse(possibleAngles.get(0));
        } else {
            bestConf = bestConfs.stream()
                    .min(Comparator.comparingDouble(conf -> Math.abs(conf.getAngle() - bestGoalAngle)))
                    .orElse(bestConfs.get(0));
        }

        return bestConf;
    }

}
