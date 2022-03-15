package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.enginesTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.DeckEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.NavigationEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.NextRound;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Position;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Circle;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Rectangle;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.Checkpoint;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.RegattaGoal;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.OarConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class NavigationEngineTest {
    DeckEngine deckEngine;
    InitGame initGame;
    NavigationEngine navigationEngine;
    NextRound nextRound;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    String JSONgame = "\n{\n  \"goal\": {\n    \"mode\": \"REGATTA\",\n    \"checkpoints\": [\n      {\n        \"position\": {\n          \"x\": 1000,\n          \"y\": 0,\n          \"orientation\": 0\n        },\n        \"shape\": {\n          \"type\": \"circle\",\n          \"radius\": 50\n        }\n      },\n      {\n        \"position\": {\n          \"x\": 0,\n          \"y\": 0,\n          \"orientation\": 0\n        },\n        \"shape\": {\n          \"type\": \"circle\",\n          \"radius\": 50\n        }\n      }\n    ]\n  },\n  \"ship\": {\n    \"type\": \"ship\",\n    \"life\": 100,\n    \"position\": {\n      \"x\": 0,\n      \"y\": 0,\n      \"orientation\": 0\n    },\n    \"name\": \"Les copaings d'abord!\",\n    \"deck\": {\n      \"width\": 3,\n      \"length\": 6\n    },\n    \"entities\": [\n      {\n        \"x\": 1,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 1,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 3,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 3,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 4,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 4,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 2,\n        \"y\": 1,\n        \"type\": \"sail\",\n        \"openned\": false\n      },\n      {\n        \"x\": 5,\n        \"y\": 0,\n        \"type\": \"rudder\"\n      }\n    ],\n    \"shape\": {\n      \"type\": \"rectangle\",\n      \"width\": 3,\n      \"height\": 6,\n      \"orientation\": 0\n    }\n  },\n  \"sailors\": [\n    {\n      \"x\": 0,\n      \"y\": 0,\n      \"id\": 0,\n      \"name\": \"Edward Teach\"\n    },\n    {\n      \"x\": 0,\n      \"y\": 1,\n      \"id\": 1,\n      \"name\": \"Edward Pouce\"\n    },\n    {\n      \"x\": 0,\n      \"y\": 2,\n      \"id\": 2,\n      \"name\": \"Tom Pouce\"\n    },\n    {\n      \"x\": 1,\n      \"y\": 0,\n      \"id\": 3,\n      \"name\": \"Jack Teach\"\n    },\n    {\n      \"x\": 1,\n      \"y\": 1,\n      \"id\": 4,\n      \"name\": \"Jack Teach\"\n    },\n    {\n      \"x\": 1,\n      \"y\": 2,\n      \"id\": 5,\n      \"name\": \"Tom Pouce\"\n    }\n  ],\n  \"shipCount\": 1\n}\n";
    String jsonNextRound = "\n{\n  \"ship\": {\n    \"type\": \"ship\",\n    \"life\": 100,\n    \"position\": {\n      \"x\": 0,\n      \"y\": 0,\n      \"orientation\": 0\n    },\n    \"name\": \"Les copaings d'abord!\",\n    \"deck\": {\n      \"width\": 3,\n      \"length\": 6\n    },\n    \"entities\": [\n      {\n        \"x\": 1,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 1,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 3,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 3,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 4,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 4,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 2,\n        \"y\": 1,\n        \"type\": \"sail\",\n        \"openned\": false\n      },\n      {\n        \"x\": 5,\n        \"y\": 0,\n        \"type\": \"rudder\"\n      }\n    ],\n    \"shape\": {\n      \"type\": \"rectangle\",\n      \"width\": 3,\n      \"height\": 6,\n      \"orientation\": 0\n    }\n  },\n  \"visibleEntities\": [\n    {\n      \"type\": \"stream\",\n      \"position\": {\n        \"x\": 500,\n        \"y\": 0,\n        \"orientation\": 0\n      },\n      \"shape\": {\n        \"type\": \"rectangle\",\n        \"width\": 50,\n        \"height\": 500,\n        \"orientation\": 0\n      },\n      \"strength\": 40\n    }\n  ],\n  \"wind\": {\n    \"orientation\": 0,\n    \"strength\": 110\n  }\n}\n";

    @BeforeEach
    void setUp() throws JsonProcessingException {
        this.initGame = OBJECT_MAPPER.readValue(JSONgame, InitGame.class);
        this.nextRound = OBJECT_MAPPER.readValue(jsonNextRound, NextRound.class);

        this.deckEngine = new DeckEngine(initGame);
        this.navigationEngine = new NavigationEngine(initGame, deckEngine);
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
        System.out.println(angles);
        assertEquals(9, angles.size());
    }
}
