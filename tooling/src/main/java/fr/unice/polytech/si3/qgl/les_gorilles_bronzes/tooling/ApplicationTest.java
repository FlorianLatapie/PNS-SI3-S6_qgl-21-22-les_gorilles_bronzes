package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.tooling;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import simulator.objects.SimulatorInfos;
import simulator.SimulatorController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ApplicationTest {
    static Path pathSimuInfos = Paths.get(System.getProperty("user.dir")+"/tooling/src/main/java/simulator/weeks/WEEK9-PREVIEW.json");
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static void main(String[] args) throws Exception {
        SimulatorController s = new SimulatorController(OBJECT_MAPPER.readValue(readFileAsString(pathSimuInfos), SimulatorInfos.class));
        s.run();
    }

    public static String readFileAsString(Path file)throws Exception {
        return new String(Files.readAllBytes(file));
    }
}
