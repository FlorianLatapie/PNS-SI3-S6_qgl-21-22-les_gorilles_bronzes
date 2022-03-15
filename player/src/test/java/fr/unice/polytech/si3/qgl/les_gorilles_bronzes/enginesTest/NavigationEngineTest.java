package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.enginesTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.Cockpit;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.DeckEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.NavigationEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.NextRound;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Position;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Rectangle;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.Checkpoint;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.RegattaGoal;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.Wind;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.OarConfiguration;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Ship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class NavigationEngineTest {
    DeckEngine deckEngine;
    InitGame initGame;
    NavigationEngine navigationEngine;
    NextRound nextRound;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    //String JSONgame = "\n{\n  \"goal\": {\n    \"mode\": \"REGATTA\",\n    \"checkpoints\": [\n      {\n        \"position\": {\n          \"x\": 1000,\n          \"y\": 0,\n          \"orientation\": 0\n        },\n        \"shape\": {\n          \"type\": \"circle\",\n          \"radius\": 50\n        }\n      },\n      {\n        \"position\": {\n          \"x\": 0,\n          \"y\": 0,\n          \"orientation\": 0\n        },\n        \"shape\": {\n          \"type\": \"circle\",\n          \"radius\": 50\n        }\n      }\n    ]\n  },\n  \"ship\": {\n    \"type\": \"ship\",\n    \"life\": 100,\n    \"position\": {\n      \"x\": 0,\n      \"y\": 0,\n      \"orientation\": 0\n    },\n    \"name\": \"Les copaings d'abord!\",\n    \"deck\": {\n      \"width\": 3,\n      \"length\": 6\n    },\n    \"entities\": [\n      {\n        \"x\": 1,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 1,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 3,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 3,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 4,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 4,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 2,\n        \"y\": 1,\n        \"type\": \"sail\",\n        \"openned\": false\n      },\n      {\n        \"x\": 5,\n        \"y\": 0,\n        \"type\": \"rudder\"\n      }\n    ],\n    \"shape\": {\n      \"type\": \"rectangle\",\n      \"width\": 3,\n      \"height\": 6,\n      \"orientation\": 0\n    }\n  },\n  \"sailors\": [\n    {\n      \"x\": 0,\n      \"y\": 0,\n      \"id\": 0,\n      \"name\": \"Edward Teach\"\n    },\n    {\n      \"x\": 0,\n      \"y\": 1,\n      \"id\": 1,\n      \"name\": \"Edward Pouce\"\n    },\n    {\n      \"x\": 0,\n      \"y\": 2,\n      \"id\": 2,\n      \"name\": \"Tom Pouce\"\n    },\n    {\n      \"x\": 1,\n      \"y\": 0,\n      \"id\": 3,\n      \"name\": \"Jack Teach\"\n    },\n    {\n      \"x\": 1,\n      \"y\": 1,\n      \"id\": 4,\n      \"name\": \"Jack Teach\"\n    },\n    {\n      \"x\": 1,\n      \"y\": 2,\n      \"id\": 5,\n      \"name\": \"Tom Pouce\"\n    }\n  ],\n  \"shipCount\": 1\n}\n";
    //String jsonNextRound = "\n{\n  \"ship\": {\n    \"type\": \"ship\",\n    \"life\": 100,\n    \"position\": {\n      \"x\": 0,\n      \"y\": 0,\n      \"orientation\": 0\n    },\n    \"name\": \"Les copaings d'abord!\",\n    \"deck\": {\n      \"width\": 3,\n      \"length\": 6\n    },\n    \"entities\": [\n      {\n        \"x\": 1,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 1,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 3,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 3,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 4,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 4,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 2,\n        \"y\": 1,\n        \"type\": \"sail\",\n        \"openned\": false\n      },\n      {\n        \"x\": 5,\n        \"y\": 0,\n        \"type\": \"rudder\"\n      }\n    ],\n    \"shape\": {\n      \"type\": \"rectangle\",\n      \"width\": 3,\n      \"height\": 6,\n      \"orientation\": 0\n    }\n  },\n  \"visibleEntities\": [\n    {\n      \"type\": \"stream\",\n      \"position\": {\n        \"x\": 500,\n        \"y\": 0,\n        \"orientation\": 0\n      },\n      \"shape\": {\n        \"type\": \"rectangle\",\n        \"width\": 50,\n        \"height\": 500,\n        \"orientation\": 0\n      },\n      \"strength\": 40\n    }\n  ],\n  \"wind\": {\n    \"orientation\": 0,\n    \"strength\": 110\n  }\n}\n";

    Path pathInitGame = Paths.get((System.getProperty("user.dir")+ "/src/test/java/fr/unice/polytech/si3/qgl/les_gorilles_bronzes/jsonForTest/InitGame.json"));
    Path pathNextRound = Paths.get((System.getProperty("user.dir")+ "/src/test/java/fr/unice/polytech/si3/qgl/les_gorilles_bronzes/jsonForTest/Week5.json"));
    Cockpit cockpit;

    public static String readFileAsString(Path file)throws Exception {
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
    void isShipInsideCheckpointTest(){
        Checkpoint[] checkpoints = ((RegattaGoal) initGame.getGoal()).getCheckpoints();

        Boolean shouldNotBe = navigationEngine.isShipInsideCheckpoint(checkpoints[navigationEngine.getNextCheckpointToReach()], nextRound.getShip());

        assertEquals(false, shouldNotBe);

        nextRound.getShip().getPosition().setX(checkpoints[navigationEngine.getNextCheckpointToReach()].getPosition().getX());
        nextRound.getShip().getPosition().setY(checkpoints[navigationEngine.getNextCheckpointToReach()].getPosition().getY());
        Boolean shouldBe = navigationEngine.isShipInsideCheckpoint(checkpoints[navigationEngine.getNextCheckpointToReach()], nextRound.getShip());
        assertEquals(true, shouldBe);
    }

    @Test
    void willBeInsideCheckpointTest(){
        Checkpoint[] checkpoints = ((RegattaGoal) initGame.getGoal()).getCheckpoints();

        OarConfiguration bestConf = new OarConfiguration(2,2,4);

        Boolean shouldNotBe = navigationEngine.willBeInsideCheckpoint(checkpoints[navigationEngine.getNextCheckpointToReach()], nextRound.getShip(), bestConf);

        assertEquals(false, shouldNotBe);

        nextRound.getShip().getPosition().setX(checkpoints[navigationEngine.getNextCheckpointToReach()].getPosition().getX() - (bestConf.getSpeed() * Math.cos(nextRound.getShip().getPosition().getOrientation())));
        nextRound.getShip().getPosition().setY(checkpoints[navigationEngine.getNextCheckpointToReach()].getPosition().getY() - (bestConf.getSpeed() * Math.sin(nextRound.getShip().getPosition().getOrientation())));
        shouldNotBe = navigationEngine.isShipInsideCheckpoint(checkpoints[navigationEngine.getNextCheckpointToReach()], nextRound.getShip());
        assertEquals(false, shouldNotBe);
        Boolean shouldBe = navigationEngine.willBeInsideCheckpoint(checkpoints[navigationEngine.getNextCheckpointToReach()], nextRound.getShip(), bestConf);
        assertEquals(true, shouldBe);


    }

    @Test
    void getDistanceTest(){
        Checkpoint[] checkpoints = ((RegattaGoal) initGame.getGoal()).getCheckpoints();
        Checkpoint checkpoint = checkpoints[navigationEngine.getNextCheckpointToReach()];

        nextRound.getShip().getPosition().setOrientation(0);

        Position position = new Position();
        position.setX(nextRound.getShip().getPosition().getX());
        position.setY(nextRound.getShip().getPosition().getY() + ((Rectangle)nextRound.getShip().getShape()).getHeight()/2 + 50);
        checkpoint.setPosition(position);

        assertEquals(50, navigationEngine.getDistance(checkpoint, nextRound.getShip()));
    }

    @Test
    void getGoalAngleTest(){
        Checkpoint[] checkpoints = ((RegattaGoal) initGame.getGoal()).getCheckpoints();

        Checkpoint checkpoint = checkpoints[navigationEngine.getNextCheckpointToReach()];
        Position position = new Position();
        position.setX(nextRound.getShip().getPosition().getX() + 50);
        position.setY(nextRound.getShip().getPosition().getY() + 50);
        checkpoint.setPosition(position);

        ((RegattaGoal) initGame.getGoal()).setCheckpoints(new Checkpoint[]{checkpoint});
        navigationEngine.computeNextRound(nextRound);

        assertEquals(Math.PI/4, navigationEngine.getGoalAngle());
    }

    @Test
    void getPossibleAnglesWithOarsTest(){
        deckEngine.placeSailors();
        List<OarConfiguration> angles = navigationEngine.getPossibleAnglesWithOars();
        assertEquals(9, angles.size());
    }

    @Test
    void addSailActionTest(){
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
        assertEquals(true, navigationEngine.shouldLiftSail());

        nextRound.getShip().setPosition(fiftyDegrees);
        assertEquals(true, navigationEngine.shouldLiftSail());

        nextRound.getShip().setPosition(ninetyDegrees);
        assertEquals(false, navigationEngine.shouldLiftSail());
    }


}
