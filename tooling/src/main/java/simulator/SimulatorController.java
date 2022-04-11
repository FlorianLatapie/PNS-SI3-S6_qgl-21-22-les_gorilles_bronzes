package simulator;


import com.fasterxml.jackson.core.JsonProcessingException;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.Cockpit;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.NextRound;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.Move;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Rectangle;
import com.fasterxml.jackson.databind.ObjectMapper;

import simulator.display.DeckPanel;
import simulator.display.DisplayPanel;
import simulator.objects.DisplayedSailor;
import simulator.objects.ActionArray;
import simulator.objects.SimulatorInfos;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class SimulatorController extends JFrame {
    private SimulatorInfos simulatorInfos;
    private Cockpit cockpit;
    private DisplayedSailor[] generatedSailors;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JFrame deckFrame;

    private DisplayPanel panel;
    private DeckPanel deckPanel;
    int multiplyFactor = 107;

    public SimulatorController(SimulatorInfos simulatorInfos) throws JsonProcessingException {
        this.simulatorInfos = simulatorInfos;
        this.cockpit = new Cockpit(true);

        init();

        // JFrame
        initDeckWindow();
        initMainWindow(deckFrame.getWidth());

        new Thread(() -> {
            try {
                while (true) {
                    panel.repaint();
                    deckPanel.repaint();
                    Thread.sleep(16);
                }
            } catch (final InterruptedException ignored) {
            }
        }).start();
    }

    private void initMainWindow(int startingXPosition) {
        setTitle("Gorille Simulator");
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        panel = new DisplayPanel(simulatorInfos);
        add(panel, BorderLayout.CENTER);

        this.setLocation(startingXPosition, 0);
        var screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        var width = (int) screenSize.getWidth();
        var height = (int) screenSize.getHeight() - 100;
        setSize(width - startingXPosition, height);
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
    }

    private void initDeckWindow() {
        var deck = simulatorInfos.getShip().getDeck();
        var entities = simulatorInfos.getShip().getEntities();
        var sailors = generatedSailors;

        deckFrame = new JFrame("Deck View");
        deckFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        var width = deck.getWidth();
        var height = deck.getLength();
        deckFrame.setSize(width * multiplyFactor, height * multiplyFactor);
        deckFrame.setVisible(true);

        deckFrame.setLayout(new BorderLayout());
        deckPanel = new DeckPanel(deck, entities, sailors);
        deckFrame.add(deckPanel, BorderLayout.CENTER);
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
        System.out.println(cockpit);
    }

    private DisplayedSailor[] generateSailors() {
        var minumumCrewSize = simulatorInfos.getMinumumCrewSize();
        var maximumCrewSize = simulatorInfos.getMaximumCrewSize();
        var deck = simulatorInfos.getShip().getDeck();
        return SimulatorModel.generateSailors(minumumCrewSize, maximumCrewSize, deck);
    }

    public void run() throws JsonProcessingException, InterruptedException {
        System.out.println("Starting simulation");

        // pause the simulation until the user presses a key
        // (very ugly, but it works)
        Scanner scanner = new Scanner(System.in);
        scanner.next();

        computeNextRound();

        run();
    }

    private void computeNextRound() throws JsonProcessingException {
        var nextRound = new NextRound();
        var ship = simulatorInfos.getShip();
        var shipPosition = ship.getPosition();

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
