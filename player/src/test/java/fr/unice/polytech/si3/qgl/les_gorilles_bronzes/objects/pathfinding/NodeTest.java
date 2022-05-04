package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.pathfinding;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.Point;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.pathfinding.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {
    List<Node> nodes;

    @BeforeEach
    void setUp() {
        nodes = new ArrayList<>();
        nodes.add(new Node(new Point(0, 0)));
        nodes.add(new Node(new Point(1, 0)));
        nodes.add(new Node(new Point(2, 0)));
        nodes.add(new Node(new Point(3, 0)));

    }

    @Test
    void compareToTest() {
        assertTrue(nodes.get(0).compareTo(nodes.get(1)) < 0);
        assertTrue(nodes.get(1).compareTo(nodes.get(0)) > 0);
        assertEquals(0, nodes.get(0).compareTo(nodes.get(0)));
    }

    @Test
    void addBranchTest() {
        nodes.get(0).addBranch(nodes.get(1));
        nodes.get(0).addBranch(nodes.get(2));
        nodes.get(0).addBranch(nodes.get(3));
        nodes.get(0).addBranch(nodes.get(3));
        assertEquals(3, nodes.get(0).getNeighbors().size());
    }

    @Test
    void equalsTest() {
        assertEquals(nodes.get(0), nodes.get(0));
        assertEquals(nodes.get(0).hashCode(), nodes.get(0).hashCode());
        assertNotEquals(nodes.get(0), new Node(new Point(0, 0)));
        assertNotEquals(nodes.get(0).hashCode(), new Node(new Point(0, 0)).hashCode());
    }

    @Test
    void toStringTest() {
        assertTrue(nodes.get(0).toString().startsWith("Node{point=Point{x=0.0, y=0.0}, internalId="));
    }
}
