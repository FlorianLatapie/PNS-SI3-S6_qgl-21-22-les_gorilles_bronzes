package simulator.display;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Deck;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Entity;
import simulator.objects.DisplayedSailor;

import javax.swing.*;
import java.awt.*;

public class DeckPanel extends JPanel {
    private Deck deck;
    private DisplayedSailor[] sailors;
    private Entity[] entities;

    private int boxSize = 100;
    private int padding = 10;

    public DeckPanel(Deck deck, Entity[] entities, DisplayedSailor[] sailors) {
        this.deck = deck;
        this.sailors = sailors;
        this.entities = entities;
    }

    @Override
    public void paintComponent(Graphics _g) {
        var g = (Graphics2D) _g;
        g.setColor(new Color(0x964B00));
        g.fillRect(0, 0, getWidth(), getHeight());

        for (var entity : entities) {
            g.setColor(Color.BLACK);
            g.drawRect(entity.getY() * boxSize, entity.getX() * boxSize, boxSize, boxSize);
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
