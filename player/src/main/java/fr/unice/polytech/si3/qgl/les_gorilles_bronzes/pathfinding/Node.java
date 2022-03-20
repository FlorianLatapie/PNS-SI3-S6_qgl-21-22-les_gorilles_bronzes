package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.pathfinding;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Point;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

//https://stackabuse.com/graphs-in-java-a-star-algorithm/
public class Node implements Comparable<Node> {
    // Id for readability of result purposes
    private static int idCounter = 0;
    public Point point;
    private int internalId;
    // Parent in the path
    public Node parent = null;

    public Map<Node, Double> neighbors;

    public double g = Double.MAX_VALUE;

    public Node(Point id) {
        this.point = id;
        this.neighbors = new HashMap<>();
        this.internalId = idCounter++;
    }

    public List<Point> findPathTo(Node target) {
        PriorityQueue<Node> closedList = new PriorityQueue<>();
        PriorityQueue<Node> openList = new PriorityQueue<>();

        openList.add(this);

        while (!openList.isEmpty()) {
            Node n = openList.peek();
            if (n == target) {
                Node n1 = target;

                List<Point> ids = new ArrayList<>();

                while (n1.parent != null) {
                    ids.add(n1.point);
                    n1 = n1.parent;
                }
                ids.add(n1.point);
                Collections.reverse(ids);

                return ids;
            }

            for (var edge : n.neighbors.entrySet()) {
                Node m = edge.getKey();
                double totalWeight = n.g + edge.getValue();

                if (!openList.contains(m) && !closedList.contains(m)) {
                    m.parent = n;
                    m.g = totalWeight;
                    openList.add(m);
                } else {
                    if (totalWeight < m.g) {
                        m.parent = n;
                        m.g = totalWeight;

                        if (closedList.contains(m)) {
                            closedList.remove(m);
                            openList.add(m);
                        }
                    }
                }
            }

            openList.remove(n);
            closedList.add(n);
        }
        return null;
    }

    @Override
    public int compareTo(Node n) {
        return (this == n) ? 0 : Comparator.<Node>comparingDouble(x -> x.g)
                .thenComparingInt(x -> x.internalId).compare(this, n);
    }

    public boolean addBranch(Node node) {
        if (neighbors.containsKey(node)) {
            return false;
        }
        neighbors.put(node, point.distanceTo(node.point));
        return true;
    }

    @Override
    public int hashCode() {
        return internalId;
    }

    @Override
    public String toString() {
        return "Node{" +
                "point=" + point +
                ", internalId=" + internalId +
                ", parent=" + parent +
                ", g=" + g +
                '}';
    }

    public static class Display extends JFrame {
        private static Display instance;
        private static List<Node> nodes;
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
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

                    g.drawOval(xCicle, yCicle, circleSize, circleSize);

                    g.setColor(Color.BLACK);
                    for (var edge : node.neighbors.entrySet()) {
                        if (fullLinks) {
                            if (edge.getKey().parent != null && edge.getKey().parent == nodes.get(0)) {
                                g.setColor(Color.BLUE);
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
}

