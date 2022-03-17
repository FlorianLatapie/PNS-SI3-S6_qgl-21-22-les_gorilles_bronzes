package fr.unice.polytech.si3.qgl.les_gorilles_bronzes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines.GlobalEngine;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.NextRound;
import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cockpit implements ICockpit {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private InitGame initGame;
    private NextRound nextRound;
    private GlobalEngine globalEngine;
    private static Logger logger = Logger.getLogger(Cockpit.class.getName());
    private static List<String> logs = new ArrayList<>();

    public Cockpit() {
        /**
         * Avoids exceptions when a JSON string contains new fields/properties
         */
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        log("Cockpit initialized");
    }

    public void initGame(String jsonGame) {
        try {
            this.initGame = OBJECT_MAPPER.readValue(jsonGame, InitGame.class);
            this.globalEngine = new GlobalEngine(initGame);
        } catch (JsonProcessingException e) {
            e.getMessage();
        }
    }

    public String nextRound(String jsonRound) {
        try {
            this.nextRound = OBJECT_MAPPER.readValue(jsonRound, NextRound.class);
            return OBJECT_MAPPER.writeValueAsString(globalEngine.computeNextRound(nextRound));
        } catch (JsonProcessingException e) {
            e.getMessage();
            return "[]";
        }
    }

    public static void log(Object object) {
        String message = object.toString();
        logs.add(message);
        logger.log(Level.INFO, message);
    }

    @Override
    public List<String> getLogs() {
        return logs;
    }

    public InitGame getInitGame() {
        return initGame;
    }

    public NextRound getNextRound() {
        return nextRound;
    }

    public GlobalEngine getGlobalEngine() {
        return globalEngine;
    }

    public void wipeLogs() {
        logs.clear();
    }
}
