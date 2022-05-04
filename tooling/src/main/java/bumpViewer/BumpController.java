package bumpViewer;


import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Rectangle;
import simulator.objects.SimulatorInfos;

import javax.swing.*;
import java.awt.*;

public class BumpController extends JFrame {
    private SimulatorInfos simulatorInfos;
    private Bump bump;
    private BumpDisplayPanel panel;

    public BumpController(SimulatorInfos parsedJson, Bump bump) {
        init(parsedJson, bump);

        // JFrame
        initMainWindow(0);

        new Thread(() -> {
            try {
                while (true) {
                    panel.repaint();
                    Thread.sleep(16);
                }
            } catch (final InterruptedException ignored) {
            }
        }).start();
    }

    private void initMainWindow(int startingXPosition) {
        setTitle("BumpViewer");
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        panel = new BumpDisplayPanel(simulatorInfos, bump);
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

    private void init(SimulatorInfos simulatorInfos, Bump bump) {
        this.simulatorInfos = simulatorInfos;
        this.bump = bump;
        initSimulatorInfos();
    }

}
