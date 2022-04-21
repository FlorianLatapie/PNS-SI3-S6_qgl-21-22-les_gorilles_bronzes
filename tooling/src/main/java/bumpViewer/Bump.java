package bumpViewer;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Bump {
    private ArrayList<Position> positions;

    public Bump(String input) throws FileNotFoundException {
        this.positions = new ArrayList<>();
        String filter = "null";
        // scans the input file line by line
        File file = new File(input);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            var splittedLine = line.split(" ");
            if (line.contains("ENTITY") && line.contains("ship")) {
                filter = splittedLine[1];
            }
            if (line.contains("POSITION") && line.contains(filter)) {
                positions.add(
                        new Position(
                                Double.parseDouble(splittedLine[2]),
                                Double.parseDouble(splittedLine[3]),
                                Double.parseDouble(splittedLine[4])
                        )
                );
            }
        }
    }

    public ArrayList<Position> getPositions() {
        return positions;
    }
}
