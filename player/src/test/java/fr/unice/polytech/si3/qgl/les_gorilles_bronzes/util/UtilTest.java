package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

 class UtilTest {
    @Test
     void clampTest() {
        assertEquals(5, Util.clamp(10, 5, 5));
        assertEquals(5, Util.clamp(-10, 5, 5));
        assertEquals(5, Util.clamp(10, -5, 5));
        assertEquals(-5, Util.clamp(-10, -5, 5));
        assertEquals(2, Util.clamp(2, -5, 5));
    }

     @Test
     void clampDoubleTest() {
         assertEquals(5.0, Util.clamp(10.0, 5.0, 5.0));
         assertEquals(5.0, Util.clamp(-10.0, 5.0, 5.0));
         assertEquals(5.0, Util.clamp(10.0, -5.0, 5.0));
         assertEquals(-5.0, Util.clamp(-10.0, -5.0, 5.0));
         assertEquals(2.0, Util.clamp(2.0, -5.0, 5.0));
     }

     @Test
     void clampAngleTest() {
        assertEquals(0.0, Util.clampAngle(0.0));
        assertEquals(Math.PI, Util.clampAngle(Math.PI));
        assertEquals(0.0, Util.clampAngle(Math.PI * 2));
        assertEquals(-Math.PI, Util.clampAngle(Math.PI * -1));
        assertEquals(0.0, Util.clampAngle(Math.PI * -2));
     }
}
