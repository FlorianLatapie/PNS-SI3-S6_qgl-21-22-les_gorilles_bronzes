package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.enginesTest.entityEnginesTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.Cockpit;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.DeckEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.NavigationEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.entityEngines.OarsEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.entityEngines.SailsEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.NextRound;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Position;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.Wind;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Voile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SailsEngineTest {

    DeckEngine deckEngine;
    InitGame initGame;
    NavigationEngine navigationEngine;
    NextRound nextRound;
    SailsEngine sailsEngine;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    Path pathInitGame = Paths.get((System.getProperty("user.dir") + "/src/test/java/fr/unice/polytech/si3/qgl/les_gorilles_bronzes/jsonForTest/InitGame.json"));
    Path pathNextRound = Paths.get((System.getProperty("user.dir") + "/src/test/java/fr/unice/polytech/si3/qgl/les_gorilles_bronzes/jsonForTest/Week5.json"));
    Cockpit cockpit;

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
        sailsEngine = new SailsEngine(deckEngine, new OarsEngine(deckEngine, navigationEngine, initGame), navigationEngine);
    }

    public static String readFileAsString(Path file) throws Exception {
        return new String(Files.readAllBytes(file));
    }

    @Test
    void addSailActionTest() {
        Position zero = new Position();
        zero.setX(0);
        zero.setY(0);
        zero.setOrientation(Math.toRadians(0));

        Position fiftyDegrees = new Position();
        fiftyDegrees.setX(0);
        fiftyDegrees.setY(0);
        fiftyDegrees.setOrientation(Math.toRadians(50));

        Position ninetyDegrees = new Position();
        ninetyDegrees.setX(0);
        ninetyDegrees.setY(0);
        ninetyDegrees.setOrientation(Math.toRadians(90));

        initGame.getShip().setPosition(zero);

        nextRound.getShip().setPosition(zero);
        assertTrue(sailsEngine.shouldLiftSail(nextRound));

        nextRound.getShip().setPosition(fiftyDegrees);
        assertTrue(sailsEngine.shouldLiftSail(nextRound));

        nextRound.getShip().setPosition(ninetyDegrees);
        assertFalse(sailsEngine.shouldLiftSail(nextRound));
    }

    @Test
    void getWindSpeedRelativeToShipTest() {
        assertEquals(100.0, sailsEngine.getWindSpeedRelativeToShip(nextRound));
    }

    @Test
    void getWindSpeedRelativeToShipTest2() {
        Wind wind = new Wind();
        wind.setOrientation(Math.toRadians(180));
        wind.setStrength(100.0);
        nextRound.setWind(wind);
        assertEquals(-100.0, sailsEngine.getWindSpeedRelativeToShip(nextRound));
    }

    @Test
    void findSailsTest() {
        Voile voile = new Voile();
        voile.setX(2);
        voile.setY(1);
        voile.setOpenned(false);
        assertEquals(List.of(voile), sailsEngine.findSails());
    }

}
