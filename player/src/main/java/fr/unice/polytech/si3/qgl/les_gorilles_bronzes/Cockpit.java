package fr.unice.polytech.si3.qgl.les_gorilles_bronzes;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.Actions.Action;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.Actions.Oar;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.NextRound;
import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;

public class Cockpit implements ICockpit {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public Cockpit() {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    }

    public void initGame(String JSONgame) {
        try {
            InitGame initGame = OBJECT_MAPPER.readValue(JSONgame, InitGame.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public String nextRound(String JSONround) {
        String defaultAnswer = " [{    \"sailorId\": 0,    \"type\": \"OAR\"  },  {    \"sailorId\": 1,    \"type\": \"OAR\"  }]";
        if (JSONround.isEmpty()) {
            return defaultAnswer;
        } else {
            try {
                NextRound nextRound = OBJECT_MAPPER.readValue(JSONround, NextRound.class);
                return OBJECT_MAPPER.writeValueAsString(new Action[]{new Oar(0),new Oar(1)});
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return "[]";
            }
        }
    }

    @Override
    public List<String> getLogs() {
        return new ArrayList<>();
    }
}
