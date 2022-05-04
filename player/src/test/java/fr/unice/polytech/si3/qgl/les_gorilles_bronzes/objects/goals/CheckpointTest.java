package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.geometry.shapes.Circle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckpointTest {
    @Test
    void checkpointTest() {
        Checkpoint checkpoint = new Checkpoint();

        assertEquals("Checkpoint{position=null, shape=null}", checkpoint.toString());


        assertTrue(checkpoint.equals(checkpoint));
        assertFalse(checkpoint.equals(null));

        assertEquals(checkpoint, new Checkpoint());
        assertEquals(checkpoint.hashCode(), new Checkpoint().hashCode());
        Circle circle = new Circle();
        circle.setRadius(1);
        checkpoint.setShape(circle);
        assertNotEquals(checkpoint, new Checkpoint());
        assertNotEquals(checkpoint.hashCode(), new Checkpoint().hashCode());
    }
}
