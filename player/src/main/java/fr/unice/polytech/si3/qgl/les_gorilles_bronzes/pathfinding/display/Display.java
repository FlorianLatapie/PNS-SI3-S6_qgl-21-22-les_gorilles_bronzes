package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.pathfinding.display;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.pathfinding.Node;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Display extends JFrame {
    private static Display instance;
    private List<Node> nodes;
    private boolean fullLinks = true;

    public static Display getInstance() {
        if (instance == null) {
            instance = new Display();
        }
        return instance;
    }

    private Display() {
        setTitle("JFrame Canvas Example");
        setSize(1800, 900);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void paintTheseNodes(List<Node> nodes) {
        this.nodes = nodes;
        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.RED);
        g.setPaintMode();

        int circleSize = 10;
        int ratio = 7;
        int offset = 200;
        if (nodes != null) {
            for (int i = 0; i < nodes.size(); i++) {
                if (i == 0 || i == 1) {
                    g.setColor(Color.BLUE);
                }
                Node node = nodes.get(i);
                int x = (int) (node.point.getX() / ratio) + offset;
                int y = (int) (node.point.getY() / ratio) + offset;

                int xCicle = (int) (node.point.getX() / ratio) + offset - circleSize / 2;
                int yCicle = (int) (node.point.getY() / ratio) + offset - circleSize / 2;

                g.fillOval(xCicle, yCicle, circleSize, circleSize);

                g.setColor(Color.BLACK);
                for (var edge : node.neighbors.entrySet()) {
                    if (fullLinks) {
                        if (edge.getKey().parent != null && edge.getKey().parent == nodes.get(0)) {
                            g.setColor(Color.BLUE);
                        } else {
                            g.setColor(Color.BLACK);
                        }
                        int x1 = (int) (edge.getKey().point.getX() / ratio) + offset;
                        int y1 = (int) (edge.getKey().point.getY() / ratio) + offset;
                        g.drawLine(x, y, x1, y1);
                    } else {
                        if (edge.getKey().parent != null && edge.getKey().parent.equals(nodes.get(0))) {
                            g.setColor(Color.BLUE);

                            int x1 = (int) (edge.getKey().point.getX() / ratio) + offset;
                            int y1 = (int) (edge.getKey().point.getY() / ratio) + offset;
                            g.drawLine(x, y, x1, y1);
                        }
                    }
                }
                g.setColor(Color.RED);
            }
        }
    }
}