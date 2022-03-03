package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles;

import org.junit.jupiter.api.Test;

import static java.lang.Math.PI;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WindTest {
    @Test
    void windTest(){
        Wind wind = new Wind();
        wind.setOrientation(PI);
        wind.setStrength(20);

        assertEquals(PI, wind.getOrientation());
        assertEquals(20, wind.getStrength());
    }
}
