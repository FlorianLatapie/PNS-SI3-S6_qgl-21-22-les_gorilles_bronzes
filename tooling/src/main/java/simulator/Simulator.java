package simulator;


import com.fasterxml.jackson.core.JsonProcessingException;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.Cockpit;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Rectangle;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Sailor;
import simulator.SimulatorInfos;
import com.fasterxml.jackson.databind.ObjectMapper;

import simulator.display.DisplayPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Simulator extends JFrame {

    private SimulatorInfos simulatorInfos;
    private Cockpit cockpit;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    private DisplayPanel panel;


    public Simulator(SimulatorInfos simulatorInfos, Cockpit cockpit) throws JsonProcessingException {
        setTitle("oui");
        setSize(500, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.simulatorInfos = simulatorInfos;
        this.cockpit = cockpit;

        init();

        setLayout(new BorderLayout());
        panel = new DisplayPanel(simulatorInfos);
        add(panel, BorderLayout.CENTER);
        setSize(1800, 900);

        new Thread(() -> {
            try {
                while (true) {
                    panel.repaint();
                    Thread.sleep(1000);
                }
            } catch (final InterruptedException e) {

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
    }

    private void initCockpit() throws JsonProcessingException {
        var initGame = new InitGame();
        initGame.setGoal(simulatorInfos.getGoal());
        initGame.setSailors(generateSailors());
        initGame.setShip(simulatorInfos.getShip());
        initGame.setShipCount(1);
        cockpit.initGame(OBJECT_MAPPER.writeValueAsString(initGame));
        System.out.println(cockpit.toString());
    }

    private Sailor[] generateSailors() {
        var ship = simulatorInfos.getShip();
        var shipDeck = ship.getDeck();
        var deckLength = shipDeck.getLength();
        var deckWidth = shipDeck.getWidth();
        var nbSailors = simulatorInfos.getMinumumCrewSize();

        var sailors = new ArrayList<Sailor>();
        var x = 0;
        var y = 0;
        for (int id = 0 ; id < nbSailors ; id++) {
           sailors.add(new Sailor(id, x, y, ""+id));
           if (x<deckWidth) {
               x++;
           } else {
               x = 0;
               y++;
               assert y >= deckLength;
           }
        }
       return sailors.toArray(new Sailor[0]);
    }

}
