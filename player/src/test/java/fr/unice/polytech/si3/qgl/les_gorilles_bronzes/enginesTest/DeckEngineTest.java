
package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.enginesTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.Cockpit;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.DeckEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.Action;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.Oar;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.enums.Direction;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Sailor;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class DeckEngineTest {
    DeckEngine deckEngine;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    Path pathNextRound = Paths.get((System.getProperty("user.dir")+ "/src/test/java/fr/unice/polytech/si3/qgl/les_gorilles_bronzes/jsonForTest/Week3.json"));
    Path pathInitGame = Paths.get((System.getProperty("user.dir")+ "/src/test/java/fr/unice/polytech/si3/qgl/les_gorilles_bronzes/jsonForTest/InitGame.json"));
    Cockpit cockpit;

    public static String readFileAsString(Path file)throws Exception {
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
        List<Entity> listEntities = Arrays.stream(cockpit.getInitGame().getShip().getEntities()).filter(e -> !e.isFree()).collect(Collectors.toList());
        assertEquals(0,listEntities.size());
        List<Sailor> listSailors = Arrays.stream(cockpit.getInitGame().getSailors()).filter(e -> !e.isFree()).collect(Collectors.toList());
        assertEquals(0,listSailors.size());
    }

    @Test
    void getOarsDirectionTest(){
        assertEquals(3, deckEngine.getOars(Direction.RIGHT).size());
        assertEquals(3, deckEngine.getOars(Direction.LEFT).size());
    }

    @Test
    void placeSailorsTest(){
        List<Sailor> sailors = Arrays.stream(cockpit.getInitGame().getSailors()).collect(Collectors.toList());
        List<Entity> entities = Arrays.stream(cockpit.getInitGame().getShip().getEntities()).collect(Collectors.toList());

        assertEquals(3,deckEngine.placeSailors(new Gouvernail()).get(0).getSailorId());
        assertFalse(sailors.get(3).isFree());
        assertFalse(entities.stream().filter(e -> e instanceof Gouvernail).collect(Collectors.toList()).get(0).isFree());
        assertEquals(4,deckEngine.placeSailors(new Voile()).get(0).getSailorId());
        assertFalse(sailors.get(4).isFree());
        assertFalse(entities.stream().filter(e -> e instanceof Voile).collect(Collectors.toList()).get(0).isFree());
        assertEquals(0, deckEngine.placeSailors(new Vigie()).size());
        assertEquals(4, deckEngine.placeSailors().size());
        assertEquals(4, deckEngine.getTotalNbSailorsOnOars());
    }

    @Test
    void getSailorByEntityTest(){
        assertEquals(0, deckEngine.getSailorByEntity(new Gouvernail()).get().getId());
    }

    @Test
    void getEntitiesByClassTest(){
        DeckEngine deckEngine = new DeckEngine(cockpit.getInitGame());

        deckEngine.setEntities(new Entity[]{new Rame(), new Rame(), new Voile(), new Voile(), new Voile(), new Vigie(), new Gouvernail()});

        assertEquals(2, deckEngine.getEntitiesByClass(new Rame()).size());
        assertEquals(3, deckEngine.getEntitiesByClass(new Voile()).size());
        assertEquals(1, deckEngine.getEntitiesByClass(new Gouvernail()).size());
        assertEquals(1, deckEngine.getEntitiesByClass(new Vigie()).size());
        assertEquals(0, deckEngine.getEntitiesByClass(new Canon()).size());
    }
}

