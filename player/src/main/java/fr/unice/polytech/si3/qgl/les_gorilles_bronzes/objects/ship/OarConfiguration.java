package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship;

import java.util.Objects;

public class OarConfiguration {
    private int leftOar;
    private int rightOar;
    private int totalOar;

    public OarConfiguration(int leftOar, int rightOar, int totalOar) { //TODO ajouter bateau pour avoir le vrai total oar
        this.leftOar = leftOar;
        this.rightOar = rightOar;
        this.totalOar = totalOar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OarConfiguration)) return false;
        OarConfiguration that = (OarConfiguration) o;
        return leftOar == that.leftOar && rightOar == that.rightOar && totalOar == that.totalOar;
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftOar, rightOar, totalOar);
    }

    public Double getAngle() {
        return ((rightOar - leftOar) * Math.PI) / totalOar;
    }

    public double getSpeed() {
        return 165*(leftOar+rightOar)/(double)totalOar;
    }

    public int getLeftOar() {
        return leftOar;
    }

    public int getRightOar() {
        return rightOar;
    }

    @Override
    public String toString() {
        return "OarConfiguration{" +
                "leftOar=" + leftOar +
                ", rightOar=" + rightOar +
                ", totalOar=" + totalOar +
                ", angle=" + getAngle() +
                '}';
    }
}