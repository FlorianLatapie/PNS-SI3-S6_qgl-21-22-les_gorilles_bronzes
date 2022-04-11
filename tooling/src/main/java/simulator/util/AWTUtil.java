package simulator.util;

import java.awt.*;
import java.util.Random;

public class AWTUtil {
    public static Color getRandomColor(int min, int max) {
        var random = new Random();
        var r = random.nextInt(max - min) + min;
        var g = random.nextInt(max - min) + min;
        var b = random.nextInt(max - min) + min;
        var color = new Color(r, g, b);
        return color;
    }
}
