package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.tooling;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import simulator.SimulatorController;
import simulator.objects.SimulatorInfos;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Application {
    static Path pathSimuInfos = Paths.get(System.getProperty("user.dir") + "/tooling/src/main/java/simulator/weeks/WEEK9-PREVIEW.json");
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static void main(String[] args) throws Exception {
        var pathSimuInfos = askUserWhichWeek();

        SimulatorController s = new SimulatorController(OBJECT_MAPPER.readValue(readFileAsString(pathSimuInfos), SimulatorInfos.class));
        System.out.println("Starting simulation");
        s.run();
    }

    private static Path askUserWhichWeek() {
        String dossierDExec = System.getProperty("user.dir") + "/tooling/src/main/java/simulator/weeks/";
        System.out.println("recherche de fichiers dans : " + dossierDExec);
        File fichiersDuDossierDExec = new File(dossierDExec);
        System.out.println("choisissez le fichier en écrivant le numéro : ");
        for (int i = 0; i < fichiersDuDossierDExec.listFiles().length; i++) {
            System.out.println(i + " : " + fichiersDuDossierDExec.listFiles()[i].getName());
        }
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();
        if (choix < 0 || choix >= fichiersDuDossierDExec.listFiles().length) {
            System.out.println("choix invalide");
            System.out.println("changement du dossier de recherche : ");
            dossierDExec = scanner.next();
            fichiersDuDossierDExec = new File(dossierDExec);
            for (int i = 0; i < fichiersDuDossierDExec.listFiles().length; i++) {
                System.out.println(i + " : " + fichiersDuDossierDExec.listFiles()[i].getName());
            }
            choix = scanner.nextInt();
        }
        return pathSimuInfos = Paths.get(dossierDExec + fichiersDuDossierDExec.listFiles()[choix].getName());

    }

    public static String readFileAsString(Path file) throws Exception {
        return new String(Files.readAllBytes(file));
    }
}
