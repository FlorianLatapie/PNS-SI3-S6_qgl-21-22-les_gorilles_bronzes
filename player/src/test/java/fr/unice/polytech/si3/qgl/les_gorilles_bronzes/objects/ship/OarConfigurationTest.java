package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OarConfigurationTest {
    OarConfiguration oarConfiguration;

    @BeforeEach
    void setUp() {
        oarConfiguration = new OarConfiguration(1, 2, 3);
    }

    @Test
    void equalsTest() {
        oarConfiguration = new OarConfiguration(1, 2, 3);

        assertTrue(oarConfiguration.equals(oarConfiguration));
        assertFalse(oarConfiguration.equals(null));
        assertFalse(oarConfiguration.equals(new Object()));

        assertEquals(oarConfiguration, new OarConfiguration(1, 2, 3));
        assertNotEquals(oarConfiguration, new OarConfiguration(1, 2, 4));

        assertEquals(oarConfiguration.hashCode(), new OarConfiguration(1, 2, 3).hashCode());
        assertNotEquals(oarConfiguration.hashCode(), new OarConfiguration(1, 2, 4).hashCode());
    }

    @Test
    void gettterAndSettersTest() {
        OarConfiguration config = new OarConfiguration(1, 2, 4);
        assertEquals(Math.PI / 4, config.getAngle());

        config = new OarConfiguration(1, 1, 2);
        assertEquals(165.0, config.getSpeed());

        config = new OarConfiguration(0, 0, 2);
        assertEquals(0.0, config.getSpeed());

        config = new OarConfiguration(0, 1, 2);
        assertEquals(165.0 / 2, config.getSpeed());

        assertEquals(1, oarConfiguration.getLeftOar());
        assertEquals(2, oarConfiguration.getRightOar());
    }

    @Test
    void toStringTest() {
        assertEquals("OarConfiguration{leftOar=1, rightOar=2, totalOar=3, angle=1.0471975511965976}", oarConfiguration.toString());
    }

}
