package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.under_engines;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.DeckEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.NextRound;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.Action;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.Oar;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.enums.Direction;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Circle;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.Checkpoint;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.RegattaGoal;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.OarConfiguration;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Ship;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.CheckedInputStream;

public class OarEngine {

    private InitGame initGame;
    private DeckEngine deckEngine;
    private NextRound nextRound;
    private int nextCheckpointToReach;

    public OarEngine(InitGame initGame, DeckEngine deckEngine, NextRound nextRound, int nextCheckpointToReach){
        this.initGame = initGame;
        this.deckEngine = deckEngine;
        this.nextRound = nextRound;
        this.nextCheckpointToReach = nextCheckpointToReach;
    }

    public List<Action> addOarAction(double bestAngle, double distanceToCheckpoint) {
        List<Action> actions = new ArrayList<>();
        OarConfiguration bestConf = findBestConfiguration(bestAngle, distanceToCheckpoint);
        var leftOars = deckEngine.getOars(Direction.LEFT).stream().limit(bestConf.getLeftOar());// take N left oars
        var rightOars = deckEngine.getOars(Direction.RIGHT).stream().limit(bestConf.getRightOar());// take M right oars

        Stream.concat(leftOars, rightOars) // we take all oars we want to activate
                .map(oar -> deckEngine.getSailorByEntity(oar)) // for each oar, we try to get the sailor that's on it
                .flatMap(Optional::stream) // we keep only the oars that do have a sailor on them
                .forEach(sailor -> actions.add(new Oar(sailor.getId()))); // we add an Oar action associated to each matching sailor
        return actions;
    }

    public OarConfiguration findBestConfiguration(double bestAngle, double distanceToCheckpoint) {
        double bestGoalAngle = bestAngle;
        var checkpoint = ((RegattaGoal) initGame.getGoal()).getCheckpoints()[nextCheckpointToReach];

        List<OarConfiguration> possibleAngles = getPossibleAnglesWithOars();
        List<OarConfiguration> bestConfs = possibleAngles.stream()
                .filter(conf -> willBeInsideCheckpoint(checkpoint, nextRound.getShip(), conf, distanceToCheckpoint))
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

    public boolean willBeInsideCheckpoint(Checkpoint checkpoint, Ship ship, OarConfiguration oarConfiguration, double distanceToCheckpoint) {
        double distance = distanceToCheckpoint - oarConfiguration.getSpeed();
        return distance <= ((Circle) checkpoint.getShape()).getRadius();
    }
}
