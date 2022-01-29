package fr.unice.polytech.si3.qgl.les_gorilles_bronzes;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;

public class Cockpit implements ICockpit {
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public Cockpit (){
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
	}

	public void initGame(String JSONgame) {
		System.out.println("Init game input: " + JSONgame);
		try {
			InitGame initGame = OBJECT_MAPPER.readValue(JSONgame, InitGame.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public String nextRound(String round) {
		//System.out.println("Next round input: " + round);
		return  " [{"
				+ "    \"sailorId\": 0,"
				+ "    \"type\": \"OAR\""
				+ "  },"
				+ "  {"
				+ "    \"sailorId\": 1,"
				+ "    \"type\": \"OAR\""
				+ "  }]";
	}

	@Override
	public List<String> getLogs() {
		return new ArrayList<>();
	}
}
