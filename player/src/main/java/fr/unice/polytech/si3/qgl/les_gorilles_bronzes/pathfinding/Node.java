package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.pathfinding;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Point;

import java.util.*;

//https://stackabuse.com/graphs-in-java-a-star-algorithm/
public class Node implements Comparable<Node> {
    // Id for readability of result purposes
    private static int idCounter = 0;
    private Point point;
    private int internalId;
    // Parent in the path
    private Node parent = null;

    private Map<Node, Double> neighbors;

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
        return new ArrayList<>();
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return internalId == node.internalId && Double.compare(node.g, g) == 0 && Objects.equals(point, node.point) && Objects.equals(parent, node.parent) && Objects.equals(neighbors, node.neighbors);
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

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Map<Node, Double> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(Map<Node, Double> neighbors) {
        this.neighbors = neighbors;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}

