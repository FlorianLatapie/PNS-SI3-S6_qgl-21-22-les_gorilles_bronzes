package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.tooling;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.Cockpit;
import simulator.SimulatorInfos;
import simulator.Simulator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ApplicationTest {
    static Path pathSimuInfos = Paths.get(System.getProperty("user.dir")+"/tooling/src/main/java/simulator/weeks/WEEK9-PREVIEW.json");
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        Cockpit cockpit = new Cockpit();
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        new Simulator(OBJECT_MAPPER.readValue(readFileAsString(pathSimuInfos), SimulatorInfos.class), cockpit);
    }

    public static String readFileAsString(Path file)throws Exception {
        return new String(Files.readAllBytes(file));
    }



}
