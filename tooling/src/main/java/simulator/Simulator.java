package simulator;


import com.fasterxml.jackson.core.JsonProcessingException;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.Cockpit;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.NextRound;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.Move;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Rectangle;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.Wind;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities.VisibleEntity;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Sailor;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Ship;
import simulator.SimulatorInfos;
import com.fasterxml.jackson.databind.ObjectMapper;

import simulator.display.DeckPanel;
import simulator.display.DisplayPanel;
import simulator.display.DisplayedSailor;

import javax.swing.*;
import java.awt.*;

public class Simulator extends JFrame {
    private SimulatorInfos simulatorInfos;
    private Cockpit cockpit;
    private DisplayedSailor[] generatedSailors;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private DisplayPanel panel;
    private DeckPanel deckPanel;
    int multiplyFactor = 107;

    public Simulator(SimulatorInfos simulatorInfos) throws JsonProcessingException {
        this.simulatorInfos = simulatorInfos;
        this.cockpit = new Cockpit();

        init();

        // JFrame
        setTitle("Gorille Simulator");
        setSize(500, 500);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        panel = new DisplayPanel(simulatorInfos);
        add(panel, BorderLayout.CENTER);

        var width = simulatorInfos.getShip().getDeck().getWidth() * multiplyFactor;
        this.setLocation(width, 0);
        setSize(1800 - width, 900);

        new Thread(() -> {
            try {
                while (true) {
                    panel.repaint();
                    deckPanel.repaint();
                    Thread.sleep(50);
                }
            } catch (final InterruptedException ignored) {
            }
        }).start();
    }

    private void initSimulatorInfos() {
        var pos = simulatorInfos.getStartingPositions();
        var ship = simulatorInfos.getShip();
        ship.setPosition(pos[0]);
        ship.setShape(new Rectangle(ship.getDeck().getWidth(), ship.getDeck().getLength(), ship.getPosition().getOrientation()));
    }

    private void init() throws JsonProcessingException {
        initSimulatorInfos();
        initCockpit();

        initDeckWindow();
    }

    private void initDeckWindow() {
        var deck = simulatorInfos.getShip().getDeck();
        var entities = simulatorInfos.getShip().getEntities();
        var sailors = generatedSailors;

        JFrame frame = new JFrame("Deck View");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        var width = deck.getWidth();
        var height = deck.getLength();
        frame.setSize(width * multiplyFactor, height * multiplyFactor);
        frame.setVisible(true);

        frame.setLayout(new BorderLayout());
        deckPanel = new DeckPanel(deck, entities, sailors);
        frame.add(deckPanel, BorderLayout.CENTER);
        deckPanel.repaint();
    }

    private void initCockpit() throws JsonProcessingException {
        var initGame = new InitGame();
        initGame.setGoal(simulatorInfos.getGoal());
        generatedSailors = generateSailors();
        initGame.setSailors(generatedSailors);
        initGame.setShip(simulatorInfos.getShip());
        initGame.setShipCount(1);
        cockpit.initGame(OBJECT_MAPPER.writeValueAsString(initGame));
    }

    private DisplayedSailor[] generateSailors() {
        var minumumCrewSize = simulatorInfos.getMinumumCrewSize();
        var maximumCrewSize = simulatorInfos.getMaximumCrewSize();
        var deck = simulatorInfos.getShip().getDeck();
        return SimulatorEngine.generateSailors(minumumCrewSize, maximumCrewSize, deck);
    }

    public void run() throws JsonProcessingException {
        var nextRound = new NextRound();
        var ship = simulatorInfos.getShip();
        var shipPosition = ship.getPosition();

        ship.setPosition(shipPosition);
        nextRound.setShip(ship);

        nextRound.setWind(simulatorInfos.getWind());

        nextRound.setVisibleEntities(simulatorInfos.getSeaEntities());

        var stringDefiningActions = cockpit.nextRound(OBJECT_MAPPER.writeValueAsString(nextRound));
        var recievedActions = OBJECT_MAPPER.readValue("{\"actions\":" + stringDefiningActions + "}", ActionArray.class);

        for (var action : recievedActions.getActions()) {
            if (action instanceof Move) {
                var move = (Move) action;
                var sailor = generatedSailors[move.getSailorId()];
                sailor.setX(sailor.getX() + move.getXdistance());
                sailor.setY(sailor.getY() + move.getYdistance());
            }
        }
    }
}
