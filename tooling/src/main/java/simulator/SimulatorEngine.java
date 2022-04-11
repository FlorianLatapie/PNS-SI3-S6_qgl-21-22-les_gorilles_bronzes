package simulator;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Deck;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Sailor;
import simulator.display.DisplayedSailor;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class SimulatorEngine {
    public static DisplayedSailor[] generateSailors(int minSailors, int maxSailors, Deck deck) {
        var deckWidth = deck.getWidth();
        var deckLength = deck.getLength();
        var nbSailors = new Random().nextInt(maxSailors - minSailors+1) + minSailors;
        System.out.println(nbSailors);

        var sailors = new ArrayList<Sailor>();
        var x = 0;
        var y = 0;
        for (int id = 0; id < nbSailors; id++) {
            var random = new Random();
            var maxColor = 256;
            var minColor = 50;
            var r = random.nextInt(maxColor- minColor) + minColor;
            var g = random.nextInt(maxColor- minColor) + minColor;
            var b = random.nextInt(maxColor- minColor) + minColor;
            var color = new Color(r, g, b);
            sailors.add(new DisplayedSailor(id, x, y, "" + id, color));
            if (x < deckWidth) {
                x++;
            } else {
                x = 0;
                y++;
                assert y >= deckLength;
            }
        }
        return sailors.toArray(new DisplayedSailor[0]);
    }
}
