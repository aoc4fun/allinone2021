package org.rt.advent.twentyone.day5;

import java.util.Objects;

public class PointInt implements  Comparable<PointInt>{
    int x;
    int y;

    public PointInt(int x, int y) {
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

    @Override
    public int compareTo(PointInt pointInt) {
        int compareX=Integer.compare(this.getX(), pointInt.getX());
        if(compareX!=0) return compareX;

        return Integer.compare(this.getY(), pointInt.getY());
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
