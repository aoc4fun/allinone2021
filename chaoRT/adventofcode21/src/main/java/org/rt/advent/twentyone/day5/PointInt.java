package org.rt.advent.twentyone.day5;

import java.util.Objects;

public class PointInt {
    int x;
    int y;

    private PointInt(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public PointInt next(Direction direction) {
        return new PointInt(getX()+direction.getDx(), getY()+direction.getDy());
    }

    public static class PointIntFactory {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PointInt)) return false;
        PointInt pointInt = (PointInt) o;
        return x == pointInt.x && y == pointInt.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return x+", "+y;
    }
}
