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
    private SimulatorModel simulatorModel;
    private Cockpit cockpit;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JFrame deckFrame;

    private DisplayPanel panel;
    private DeckPanel deckPanel;
    int multiplyFactor = 107;

    public SimulatorController(SimulatorInfos parsedJson) throws JsonProcessingException {
       init(parsedJson);

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

    private void init(SimulatorInfos simulatorInfos) throws JsonProcessingException {
        this.simulatorInfos = simulatorInfos;
        this.simulatorModel = new SimulatorModel(simulatorInfos);
        this.cockpit = new Cockpit(true);

        initSimulatorInfos();
        initCockpit();
    }

    private void initDeckWindow() {
        deckFrame = new JFrame("Deck View");
        deckFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        var deck = simulatorInfos.getShip().getDeck();
        var width = deck.getWidth();
        var height = deck.getLength();

        deckFrame.setSize(width * multiplyFactor, height * multiplyFactor);
        deckFrame.setVisible(true);

        deckFrame.setLayout(new BorderLayout());
        deckPanel = new DeckPanel(simulatorModel, simulatorInfos);
        deckFrame.add(deckPanel, BorderLayout.CENTER);
        deckPanel.repaint();
    }

    private void initCockpit() throws JsonProcessingException {
        var initGame = new InitGame();
        initGame.setGoal(simulatorInfos.getGoal());
        initGame.setSailors(generateSailors());
        initGame.setShip(simulatorInfos.getShip());
        initGame.setShipCount(1);
        cockpit.initGame(OBJECT_MAPPER.writeValueAsString(initGame));
    }

    private DisplayedSailor[] generateSailors() {
        var minumumCrewSize = simulatorInfos.getMinumumCrewSize();
        var maximumCrewSize = simulatorInfos.getMaximumCrewSize();
        var deck = simulatorInfos.getShip().getDeck();
        return simulatorModel.generateSailors(minumumCrewSize, maximumCrewSize, deck);
    }

    public void run() throws JsonProcessingException, InterruptedException {
        System.out.println("new step in simulation");

        // pause the simulation until the user presses a key
        // (very ugly, but it works)
        Scanner scanner = new Scanner(System.in);
        scanner.next();

        computeNextRound();

        run();
    }

    private void computeNextRound() throws JsonProcessingException {
        var nextRound = simulatorModel.generateNextRound();
        var nextRoundJSON = OBJECT_MAPPER.writeValueAsString(nextRound);

        var stringDefiningActions = cockpit.nextRound(nextRoundJSON);
        var receivedActions = OBJECT_MAPPER.readValue("{\"actions\":" + stringDefiningActions + "}", ActionArray.class);

        simulatorModel.applyActions(receivedActions.getActions());
    }
}
