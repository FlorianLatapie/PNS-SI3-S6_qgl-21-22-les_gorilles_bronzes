package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UtilTest {
    @Test
     void clampTest() {
        assertEquals(6, Util.clamp(10, 5, 6));
        assertEquals(5, Util.clamp(-10, 5, 6));
        assertEquals(6, Util.clamp(10, -5, 6));
        assertEquals(-5, Util.clamp(-10, -5, 6));
        assertEquals(2, Util.clamp(2, -5, 6));
        Throwable exception = assertThrows(Exception.class, () -> Util.clamp(1000, 10, 5));
        assertEquals("min > max (10 > 5)", exception.getMessage());
    }

     @Test
     void clampDoubleTest() {
         assertEquals(6.0, Util.clamp(10.0, 5.0, 6.0));
         assertEquals(5.0, Util.clamp(-10.0, 5.0, 6.0));
         assertEquals(6.0, Util.clamp(10.0, -5.0, 6.0));
         assertEquals(-5.0, Util.clamp(-10.0, -5.0, 6.0));
         assertEquals(2.0, Util.clamp(2.0, -5.0, 6.0));
         Throwable exception = assertThrows(Exception.class, () -> Util.clamp(1000, 10, 5));
         assertEquals("min > max (10 > 5)", exception.getMessage());
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