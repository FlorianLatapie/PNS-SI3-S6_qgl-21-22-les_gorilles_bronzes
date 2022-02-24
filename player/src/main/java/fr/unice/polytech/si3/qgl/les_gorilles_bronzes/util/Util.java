package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.util;

public class Util {

    public static double clamp (double value, double min, double max){
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }
}
