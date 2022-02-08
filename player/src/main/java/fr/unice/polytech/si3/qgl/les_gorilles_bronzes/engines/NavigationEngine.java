package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.Actions.Action;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.Actions.Oar;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.NextRound;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.RegattaGoal;
import java.util.*;
import java.util.stream.Stream;

public class NavigationEngine {
    private InitGame initGame;
    private NextRound nextRound;
    private DeckEngine deckEngine;
    private int nbCheckpointsAlreadyReached = -1;

    public NavigationEngine(InitGame initGame, DeckEngine deckEngine) {
        this.initGame = initGame;
        this.deckEngine = deckEngine;
    }

    public List<Action> computeNextRound(NextRound nextRound) {
        List<Action> actions = new ArrayList();
        this.nextRound = nextRound;

        double goalAngle = getGoalAngle();
        int nbOars = initGame.getSailors().length;
        List<OarConfiguration> possibleAngles = new ArrayList<>();
        for (int i = 0; i <= nbOars / 2; i++) {
            for (int j = 0; j <= nbOars / 2; j++) {
                possibleAngles.add(new OarConfiguration(i, j, nbOars));
            }
        }

        possibleAngles.remove(0); // removing 0 oars on each side

        OarConfiguration bestConf = possibleAngles.stream()//NOSONAR
                .min(Comparator.comparingDouble(conf -> Math.abs(conf.getAngle() - goalAngle)))
                .get();

        var leftOars = deckEngine.getLeftOars().stream().limit(bestConf.leftOar);// take N left oars
        var rightOars = deckEngine.getRightOars().stream().limit(bestConf.rightOar);// take M right oars
        Stream.concat(leftOars, rightOars) // we take all oars we want to activate
                .map(oar -> deckEngine.getSailorByEntity(oar)) // for each oar, we try to get the sailor that's on it
                .flatMap(Optional::stream) // we keep only the oars that do have a sailor on them
                .forEach(sailor -> actions.add(new Oar(sailor.getId()))); // we add an Oar action associated to each matching sailor

        return actions;
    }

    private double getGoalAngle() {
        var boatPosition = nextRound.getShip().getPosition();
        var checkpointPosition = ((RegattaGoal) initGame.getGoal()).getCheckpoints()[nbCheckpointsAlreadyReached + 1].getPosition();
        var res = Math.atan2(checkpointPosition.getY() - boatPosition.getY(), checkpointPosition.getX() - boatPosition.getX()) - boatPosition.getOrientation();
        while (res < -Math.PI)
            res += 2 * Math.PI;
        while (res > Math.PI)
            res -= 2 * Math.PI;
        return res;
    }

    public static boolean almostEqual(double a, double b, double eps) {
        return Math.abs(a - b) < eps;
    }

    class OarConfiguration {
        int leftOar, rightOar, totalOar;

        public OarConfiguration(int leftOar, int rightOar, int totalOar) {
            this.leftOar = leftOar;
            this.rightOar = rightOar;
            this.totalOar = totalOar;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof OarConfiguration)) return false;
            OarConfiguration that = (OarConfiguration) o;
            return leftOar == that.leftOar && rightOar == that.rightOar && totalOar == that.totalOar;
        }

        @Override
        public int hashCode() {
            return Objects.hash(leftOar, rightOar, totalOar);
        }

        public Double getAngle() {
            return ((rightOar - leftOar) * Math.PI) / totalOar;
        }

        public double getSpeed() {
            return rightOar + leftOar;
        }

        @Override
        public String toString() {
            return "OarConfiguration{" +
                    "leftOar=" + leftOar +
                    ", rightOar=" + rightOar +
                    ", totalOar=" + totalOar +
                    ", angle=" + getAngle() +
                    '}';
        }
    }
}
