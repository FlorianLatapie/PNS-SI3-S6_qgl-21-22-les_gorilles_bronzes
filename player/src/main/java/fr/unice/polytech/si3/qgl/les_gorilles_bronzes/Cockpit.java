package fr.unice.polytech.si3.qgl.les_gorilles_bronzes;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.DeckEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.GlobalEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.Actions.Action;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.Actions.Oar;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.NextRound;
import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;

public class Cockpit implements ICockpit {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private InitGame initGame;
    private NextRound nextRound;
    private GlobalEngine globalEngine;

    public Cockpit() {
        /**
         * Avoids exceptions when a JSON string contains new fields/properties
         */
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public void initGame(String JSONgame) {
        try {
            this.initGame = OBJECT_MAPPER.readValue(JSONgame, InitGame.class);
            this.globalEngine = new GlobalEngine(initGame);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public String nextRound(String JSONround) {
        try {
            this.nextRound = OBJECT_MAPPER.readValue(JSONround, NextRound.class);
            return OBJECT_MAPPER.writeValueAsString(globalEngine.computeNextRound(nextRound));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[]";
        }
    }

    @Override
    public List<String> getLogs() {
        return new ArrayList<>();
    }

    public InitGame getInitGame() {
        return initGame;
    }

    public NextRound getNextRound() {
        return nextRound;
    }
}
