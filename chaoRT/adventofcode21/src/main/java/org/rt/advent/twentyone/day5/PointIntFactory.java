package org.rt.advent.twentyone.day5;

public class PointIntFactory {
    private PointIntFactory() {
        super();
    }

    public static PointInt createFromString(String def) {
        String[] coords = def.split(",");
        return new PointInt(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
    }

    public static PointInt createFromCoords(int x, int y) {

        return new PointInt(x, y);
    }
}
