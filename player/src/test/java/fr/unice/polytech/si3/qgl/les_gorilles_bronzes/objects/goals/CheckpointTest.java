package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CheckpointTest {
    @Test
    void checkpointTest(){
        Checkpoint checkpoint = new Checkpoint();
        assertEquals("Checkpoint{position=null, shape=null}", checkpoint.toString());
    }
}
