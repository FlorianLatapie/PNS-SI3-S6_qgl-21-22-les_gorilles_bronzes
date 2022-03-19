package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.pathfinding;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Point;

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

    public Node(Point id){
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

    public boolean addBranch(Node node){
        if (neighbors.containsKey(node)){
            return false;
        }
        neighbors.put(node, point.distanceTo(node.point));
        return true;
    }

    @Override
    public int hashCode() {
        return internalId;
    }
}
