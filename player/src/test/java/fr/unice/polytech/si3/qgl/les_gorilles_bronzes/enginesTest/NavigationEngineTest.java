package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.enginesTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.Cockpit;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.DeckEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.NavigationEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.NextRound;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.Oar;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.Turn;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Position;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Rectangle;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.Checkpoint;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.RegattaGoal;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.OarConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class NavigationEngineTest {
    DeckEngine deckEngine;
    InitGame initGame;
    NavigationEngine navigationEngine;
    NextRound nextRound;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    Path pathInitGame = Paths.get((System.getProperty("user.dir") + "/src/test/java/fr/unice/polytech/si3/qgl/les_gorilles_bronzes/jsonForTest/InitGame.json"));
    Path pathNextRound = Paths.get((System.getProperty("user.dir") + "/src/test/java/fr/unice/polytech/si3/qgl/les_gorilles_bronzes/jsonForTest/Week5.json"));
    Cockpit cockpit;

    public static String readFileAsString(Path file) throws Exception {
        return new String(Files.readAllBytes(file));
    }

    @BeforeEach
    void setUp() throws Exception {
        cockpit = new Cockpit();
        String strInitGame = readFileAsString(pathInitGame);
        cockpit.initGame(strInitGame);
        String strNextRound = readFileAsString(pathNextRound);
        cockpit.nextRound(strNextRound);

        nextRound = cockpit.getNextRound();
        initGame = cockpit.getInitGame();

        deckEngine = cockpit.getGlobalEngine().getDeckEngine();
        navigationEngine = cockpit.getGlobalEngine().getNavigationEngine();
    }

    @Test
    void isShipInsideCheckpointTest() {
        Checkpoint[] checkpoints = ((RegattaGoal) initGame.getGoal()).getCheckpoints();

        Boolean shouldNotBe = navigationEngine.isShipInsideCheckpoint(checkpoints[navigationEngine.getNextCheckpointToReach()], nextRound.getShip());

        assertFalse(shouldNotBe);

        nextRound.getShip().getPosition().setX(checkpoints[navigationEngine.getNextCheckpointToReach()].getPosition().getX());
        nextRound.getShip().getPosition().setY(checkpoints[navigationEngine.getNextCheckpointToReach()].getPosition().getY());
        Boolean shouldBe = navigationEngine.isShipInsideCheckpoint(checkpoints[navigationEngine.getNextCheckpointToReach()], nextRound.getShip());
        assertTrue(shouldBe);
    }

    @Test
    void willBeInsideCheckpointTest() {
        Checkpoint[] checkpoints = ((RegattaGoal) initGame.getGoal()).getCheckpoints();

        OarConfiguration bestConf = new OarConfiguration(2, 2, 4);

        Boolean shouldNotBe = navigationEngine.willBeInsideCheckpoint(checkpoints[navigationEngine.getNextCheckpointToReach()], nextRound.getShip(), bestConf);

        assertFalse(shouldNotBe);

        nextRound.getShip().getPosition().setX(checkpoints[navigationEngine.getNextCheckpointToReach()].getPosition().getX() - (bestConf.getSpeed() * Math.cos(nextRound.getShip().getPosition().getOrientation())));
        nextRound.getShip().getPosition().setY(checkpoints[navigationEngine.getNextCheckpointToReach()].getPosition().getY() - (bestConf.getSpeed() * Math.sin(nextRound.getShip().getPosition().getOrientation())));
        shouldNotBe = navigationEngine.isShipInsideCheckpoint(checkpoints[navigationEngine.getNextCheckpointToReach()], nextRound.getShip());
        assertFalse(shouldNotBe);
        Boolean shouldBe = navigationEngine.willBeInsideCheckpoint(checkpoints[navigationEngine.getNextCheckpointToReach()], nextRound.getShip(), bestConf);
        assertTrue(shouldBe);


    }

    @Test
    void getDistanceTest() {
        Checkpoint[] checkpoints = ((RegattaGoal) initGame.getGoal()).getCheckpoints();
        Checkpoint checkpoint = checkpoints[navigationEngine.getNextCheckpointToReach()];

        nextRound.getShip().getPosition().setOrientation(0);

        Position position = new Position();
        position.setX(nextRound.getShip().getPosition().getX());
        position.setY(nextRound.getShip().getPosition().getY() + ((Rectangle) nextRound.getShip().getShape()).getHeight() / 2 + 50);
        checkpoint.setPosition(position);

        assertEquals(50, navigationEngine.getDistanceToCheckpoint(checkpoint, nextRound.getShip()));
    }

    @Test
    void getGoalAngleTest() {
        Checkpoint[] checkpoints = ((RegattaGoal) initGame.getGoal()).getCheckpoints();

        Checkpoint checkpoint = checkpoints[navigationEngine.getNextCheckpointToReach()];
        Position position = new Position();
        position.setX(nextRound.getShip().getPosition().getX() + 50);
        position.setY(nextRound.getShip().getPosition().getY() + 50);
        checkpoint.setPosition(position);

        ((RegattaGoal) initGame.getGoal()).setCheckpoints(new Checkpoint[]{checkpoint});
        var res = navigationEngine.computeNextRound(nextRound);
        assertNotNull(res);
        assertFalse(res.isEmpty());

        assertEquals(Math.PI / 4, navigationEngine.getGoalAngle());
    }


    @Test
    void turnShipWithBestConfigurationTest() {
        //Turn sailorId=3{rotation=0.0}, Oar sailorId=0, Oar sailorId=1, Oar sailorId=2, Oar sailorId=5
        assertEquals(List.of(
                new Turn(3, 0.0),
                new Oar(0),
                new Oar(1),
                new Oar(2),
                new Oar(5)
        ), navigationEngine.turnShipWithBestConfiguration());
    }


}
