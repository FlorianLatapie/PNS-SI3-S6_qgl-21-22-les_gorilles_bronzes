package bumpViewer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import simulator.SimulatorController;
import simulator.objects.SimulatorInfos;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class BumpViewerMain {
    private static Path pathSimuInfos = Paths.get(System.getProperty("user.dir")
            + "/tooling/src/main/java/bumpViewer/input/"
            + "WEEK10.json"
    );
    private static Path pathBump = Paths.get(System.getProperty("user.dir")
            + "/tooling/src/main/java/bumpViewer/input/"
            + "output.bump"
    );
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static void main(String[] args) throws Exception {
        Bump bump = new Bump(pathBump.toString());
        new BumpController(OBJECT_MAPPER.readValue(readFileAsString(pathSimuInfos), SimulatorInfos.class), bump);
    }

    public static String readFileAsString(Path file) throws Exception {
        return new String(Files.readAllBytes(file));
    }
}
