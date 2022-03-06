package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.Math.PI;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WindTest {
    Wind wind;
    @BeforeEach
    void setUp() {
        wind = new Wind();
        wind.setOrientation(PI);
        wind.setStrength(20);
    }

    @Test
    void windTest(){
        assertEquals(PI, wind.getOrientation());
        assertEquals(20, wind.getStrength());
    }

    @Test
    void equalsTest(){
        Wind wind2 = new Wind();
        wind2.setOrientation(PI);
        wind2.setStrength(20);

        assertEquals(wind, wind2);
        assertEquals(wind.hashCode(), wind2.hashCode());
    }

    @Test
    void toStringTest(){
        assertEquals("Wind{orientation=3.141592653589793, strength=20.0}", wind.toString());
    }
}
