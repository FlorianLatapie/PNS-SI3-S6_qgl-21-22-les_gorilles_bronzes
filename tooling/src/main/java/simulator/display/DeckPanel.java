package simulator.display;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Deck;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Entity;
import simulator.SimulatorModel;
import simulator.objects.DisplayedSailor;
import simulator.objects.SimulatorInfos;

import javax.swing.*;
import java.awt.*;

public class DeckPanel extends JPanel {
    private Deck deck;
    private DisplayedSailor[] sailors;
    private Entity[] entities;

    private int boxSize = 100;
    private int padding = 10;

    public DeckPanel(SimulatorModel model, SimulatorInfos simulatorInfos) {
        this.deck = simulatorInfos.getShip().getDeck();
        this.sailors = model.getSailors();
        this.entities = simulatorInfos.getShip().getEntities();
    }

    @Override
    public void paintComponent(Graphics _g) {
        var g = (Graphics2D) _g;
        g.setColor(new Color(0x964B00));
        g.fillRect(0, 0, getWidth(), getHeight());

        for (var entity : entities) {
            // darker brown than the background
            g.setColor(new Color(0x8B4513));
            g.fillRect(entity.getY() * boxSize, entity.getX() * boxSize, boxSize, boxSize);
            g.setColor(Color.BLACK);
            g.drawString(entity.getClass().getSimpleName() + "", entity.getY() * boxSize + padding/2, entity.getX() * boxSize + padding +2);
        }
        for (var sailor : sailors) {
            g.setColor(sailor.getColor());
            g.fillOval(sailor.getY() * boxSize + padding, sailor.getX() * boxSize + padding, boxSize - 2 * padding, boxSize - 2 * padding);
            g.setColor(Color.BLACK);
            g.drawString(sailor.getId() + "", sailor.getY() * boxSize + 40, sailor.getX() * boxSize + 50);
        }
    }
}
