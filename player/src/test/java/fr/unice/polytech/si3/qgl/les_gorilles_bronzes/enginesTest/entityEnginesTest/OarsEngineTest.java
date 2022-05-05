package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.enginesTest.entityEnginesTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.Cockpit;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.DeckEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.NavigationEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.entityEngines.OarsEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.NextRound;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.OarConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OarsEngineTest {
    DeckEngine deckEngine;
    InitGame initGame;
    NavigationEngine navigationEngine;
    NextRound nextRound;
    OarsEngine oarsEngine;

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
        oarsEngine = new OarsEngine(deckEngine, navigationEngine, initGame);
    }

    public static String readFileAsString(Path file) throws Exception {
        return new String(Files.readAllBytes(file));
    }

    @Test
    void getPossibleAnglesWithOarsTest() {
        deckEngine.placeSailors();
        List<OarConfiguration> angles = oarsEngine.getPossibleAnglesWithOars();
        assertEquals(9, angles.size());
    }

}
