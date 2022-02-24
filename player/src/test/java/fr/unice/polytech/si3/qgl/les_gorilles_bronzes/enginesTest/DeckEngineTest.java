
package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.enginesTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.Cockpit;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.DeckEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.enums.Direction;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeckEngineTest {
    DeckEngine deckEngine;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    Path pathNextRound = Paths.get((System.getProperty("user.dir")+ "/src/test/java/fr/unice/polytech/si3/qgl/les_gorilles_bronzes/jsonForTest/Week3.json"));
    Path pathInitGame = Paths.get((System.getProperty("user.dir")+ "/src/test/java/fr/unice/polytech/si3/qgl/les_gorilles_bronzes/jsonForTest/InitGame.json"));
    Cockpit cockpit;

    public static String readFileAsString(Path file)throws Exception
    {
        return new String(Files.readAllBytes(file));
    }

    @BeforeEach
    void setUp() throws Exception {
        cockpit = new Cockpit();
        String strInitGame = readFileAsString(pathInitGame);
        cockpit.initGame(strInitGame);
        this.deckEngine = new DeckEngine(cockpit.getInitGame());
    }

    @Test
    void beforeEachRoundTest(){
        deckEngine.beforeEachRound();
        List<Entity> list = Arrays.stream(cockpit.getInitGame().getShip().getEntities()).filter(e -> !e.isFree()).collect(Collectors.toList());
        assertEquals(0,list.size());
    }

    @Test
    void getOarsDirectionTest(){
        assertEquals(3, deckEngine.getOars(Direction.RIGHT).size());
        assertEquals(3, deckEngine.getOars(Direction.LEFT).size());
    }

    /*@Test
    void sailorsWhoDontHaveAnOarTest() {
        assertEquals(4, deckEngine.sailorsWhoDontHaveAnOar().size());
        deckEngine.placeSailorsOnOars(2, DeckEngine.Direction.LEFT);
        assertEquals(2, deckEngine.sailorsWhoDontHaveAnOar().size());
    }*/



}

