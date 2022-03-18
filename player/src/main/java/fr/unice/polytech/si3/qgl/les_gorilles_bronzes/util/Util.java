package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.util;

public class Util {
    public static final double DOUBLE_VALUES_EQUALS_PRECISION = 1e-6;

    private Util() {
    }

    public static int clamp(int value, int min, int max) {
        if (min > max)
            throw new IllegalArgumentException("min > max (" + min + " > " + max + ")");
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    public static double clamp(double value, double min, double max) {
        if (min > max)
            throw new IllegalArgumentException("min > max (" + min + " > " + max + ")");
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    public static double clampAngle(double value) {
        while (value < -Math.PI)
            value += 2 * Math.PI;
        while (value > Math.PI)
            value -= 2 * Math.PI;
        return value;
    }

    public static boolean areTheseDoubleAboutEqual(double x1, double x2) {
        return Math.abs(x1 - x2) < Util.DOUBLE_VALUES_EQUALS_PRECISION;
    }
}
