package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship;

import java.util.Objects;

public class Deck {
    private int width;
    private int length;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deck deck = (Deck) o;
        return width == deck.width && length == deck.length;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, length);
    }

    @Override
    public String toString() {
        return "Deck{" +
                "width=" + width +
                ", length=" + length +
                '}';
    }
}
