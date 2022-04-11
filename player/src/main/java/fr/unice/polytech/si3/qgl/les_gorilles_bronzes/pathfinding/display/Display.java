package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.pathfinding.display;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.pathfinding.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.io.Serializable;
import java.util.List;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Point;

public class Display extends JFrame {
    private static Display instance;
    private List<Node> nodes;
    private List<Point> path;

    private int offsetX = 0;
    private int offsetY = 0;

    private IntPoint mouseOrigin;

    private static class IntPoint implements Serializable {
        public final int x;
        public final int y;

        public IntPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static Display getInstance() {
        if (instance == null) {
            instance = new Display();
        }
        return instance;
    }

    private Display() {
        setTitle("Pathfinding");
        setSize(1800, 900);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    mouseOrigin = new IntPoint(e.getX(), e.getY());
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                if (mouseOrigin != null) {
                    offsetX += (e.getX() - mouseOrigin.x) ;
                    offsetY += (e.getY() - mouseOrigin.y) ;
                    mouseOrigin = new IntPoint(e.getX(), e.getY());
                    repaint();
                }
            }
        });
    }

    public void paintTheseNodes(List<Node> nodes, List<Point> path) {
        this.nodes = nodes;
        this.path = path;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.RED);
        g.setPaintMode();

        int circleSize = 10;
        int ratio = 7;
        if (nodes != null) {
            for (int i = 0; i < nodes.size(); i++) {
                Node node = nodes.get(i);

                if (i == 0 || i == 1) {
                    g.setColor(Color.BLUE);
                } else if (path.contains(node.getPoint())){
                    g.setColor(Color.GREEN);
                }
                int x = (int) (node.getPoint().getX() / ratio) + offsetX;
                int y = (int) (node.getPoint().getY() / ratio) + offsetY;

                int xCicle = (int) (node.getPoint().getX() / ratio) + offsetX - circleSize / 2;
                int yCicle = (int) (node.getPoint().getY() / ratio) + offsetY - circleSize / 2;

                g.fillOval(xCicle, yCicle, circleSize, circleSize);

                g.setColor(Color.BLACK);
                for (var edge : node.getNeighbors().entrySet()) {
                    int x1 = (int) (edge.getKey().getPoint().getX() / ratio) + offsetX;
                    int y1 = (int) (edge.getKey().getPoint().getY() / ratio) + offsetY;
                    g.drawLine(x, y, x1, y1);

                }
            }
            g.setColor(Color.RED);
        }
    }
}
