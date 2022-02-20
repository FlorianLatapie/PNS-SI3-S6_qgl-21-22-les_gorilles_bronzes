
package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.enginesTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.DeckEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeckEngineTest {
    DeckEngine deckEngine;
    private InitGame initGame;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    String JSONgame = "\n{\n  \"goal\": {\n    \"mode\": \"REGATTA\",\n    \"checkpoints\": [\n      {\n        \"position\": {\n          \"x\": 1000,\n          \"y\": 0,\n          \"orientation\": 0\n        },\n        \"shape\": {\n          \"type\": \"circle\",\n          \"radius\": 50\n        }\n      },\n      {\n        \"position\": {\n          \"x\": 0,\n          \"y\": 0,\n          \"orientation\": 0\n        },\n        \"shape\": {\n          \"type\": \"circle\",\n          \"radius\": 50\n        }\n      }\n    ]\n  },\n  \"ship\": {\n    \"type\": \"ship\",\n    \"life\": 100,\n    \"position\": {\n      \"x\": 0,\n      \"y\": 0,\n      \"orientation\": 0\n    },\n    \"name\": \"Les copaings d'abord!\",\n    \"deck\": {\n      \"width\": 3,\n      \"length\": 6\n    },\n    \"entities\": [\n      {\n        \"x\": 1,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 1,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 3,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 3,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 4,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 4,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 2,\n        \"y\": 1,\n        \"type\": \"sail\",\n        \"openned\": false\n      },\n      {\n        \"x\": 5,\n        \"y\": 0,\n        \"type\": \"rudder\"\n      }\n    ],\n    \"shape\": {\n      \"type\": \"rectangle\",\n      \"width\": 3,\n      \"height\": 6,\n      \"orientation\": 0\n    }\n  },\n  \"sailors\": [\n    {\n      \"x\": 0,\n      \"y\": 0,\n      \"id\": 0,\n      \"name\": \"Edward Teach\"\n    },\n    {\n      \"x\": 0,\n      \"y\": 1,\n      \"id\": 1,\n      \"name\": \"Edward Pouce\"\n    },\n    {\n      \"x\": 0,\n      \"y\": 2,\n      \"id\": 2,\n      \"name\": \"Tom Pouce\"\n    },\n    {\n      \"x\": 1,\n      \"y\": 0,\n      \"id\": 3,\n      \"name\": \"Jack Teach\"\n    },\n    {\n      \"x\": 1,\n      \"y\": 1,\n      \"id\": 4,\n      \"name\": \"Jack Teach\"\n    },\n    {\n      \"x\": 1,\n      \"y\": 2,\n      \"id\": 5,\n      \"name\": \"Tom Pouce\"\n    }\n  ],\n  \"shipCount\": 1\n}\n";

    @BeforeEach
    void setUp() throws JsonProcessingException {
        this.initGame = OBJECT_MAPPER.readValue(JSONgame, InitGame.class);
        this.deckEngine = new DeckEngine(initGame);
    }

    @Test
    void getOarsTest() {
        assertEquals(6, deckEngine.getOars().size());
    }

    /*@Test
    void sailorsWhoDontHaveAnOarTest() {
        assertEquals(4, deckEngine.sailorsWhoDontHaveAnOar().size());
        deckEngine.placeSailorsOnOars(2, DeckEngine.Direction.LEFT);
        assertEquals(2, deckEngine.sailorsWhoDontHaveAnOar().size());
    }

    @Test
    void sailorsWhoHaveAnOarTest() {
        assertEquals(2, deckEngine.sailorsWhoHaveAnOar().size());
        deckEngine.placeSailorsOnOars(2, DeckEngine.Direction.LEFT);
        assertEquals(4, deckEngine.sailorsWhoHaveAnOar().size());
    }

    @Test
    void moveSailorsToOars() {
        assertEquals(2, deckEngine.sailorsWhoHaveAnOar().size());
        assertEquals(4, deckEngine.oarsAvailable().size());
        deckEngine.placeSailorsOnOars(2, DeckEngine.Direction.LEFT);
        assertEquals(4, deckEngine.sailorsWhoHaveAnOar().size()); // +2 sailors with an oar
        assertEquals(3, deckEngine.oarsAvailable().size()); // -1 oar available
        deckEngine.placeSailorsOnOars(3, DeckEngine.Direction.RIGHT);
        assertEquals(6, deckEngine.sailorsWhoHaveAnOar().size()); // + 2 sailors with an oar
        assertEquals(1, deckEngine.oarsAvailable().size()); // -2 oars available
    }*/
}

