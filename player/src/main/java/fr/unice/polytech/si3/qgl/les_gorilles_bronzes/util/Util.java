package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.util;

public class Util {

    public static int clamp (int value, int min, int max){
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    public static double clamp (double value, double min, double max){
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    public static double clampAngle (double value) {
        while (value < -Math.PI)
            value += 2 * Math.PI;
        while (value > Math.PI)
            value -= 2 * Math.PI;
        return value;
    }
}
