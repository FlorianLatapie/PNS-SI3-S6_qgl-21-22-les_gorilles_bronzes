package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.pathfinding;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Node implements Comparable<Node> {
    // Id for readability of result purposes
    private static int idCounter = 0;
    public Point id;

    // Parent in the path
    public Node parent = null;

    public List<Edge> neighbors;

    // Evaluation functions
    public double f = Double.MAX_VALUE;
    public double g = Double.MAX_VALUE;


    public Node(Point id){
        this.id = id;
        this.neighbors = new ArrayList<>();
    }

    public List<Point> findPathTo(Node target) {
        PriorityQueue<Node> closedList = new PriorityQueue<>();
        PriorityQueue<Node> openList = new PriorityQueue<>();

        openList.add(this);

        while (!openList.isEmpty()) {
            Node n = openList.peek();
            if (n == target) {
                Node n1 = this;

                List<Point> ids = new ArrayList<>();

                while (n1.parent != null) {
                    ids.add(n1.id);
                    n1 = n1.parent;
                }
                ids.add(n1.id);
                Collections.reverse(ids);

                return ids;
            }

            for (Edge edge : n.neighbors) {
                Node m = edge.node;
                double totalWeight = n.g + edge.weight;

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
        return Double.compare(this.g, n.g);
    }

    public static class Edge {
        Edge(double weight, Node node){
            this.weight = weight;
            this.node = node;
        }

        public double weight;
        public Node node;
    }

    public void addBranch(double weight, Node node){
        Edge newEdge = new Edge(weight, node);
        neighbors.add(newEdge);
    }
}
