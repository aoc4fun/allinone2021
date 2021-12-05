package org.rt.advent.twentyone.day5;

import java.util.Objects;

public class Line {
    PointInt source;
    PointInt target;

    private Line(PointInt source, PointInt target) {
        this.source=source;
        this.target=target;
    }

    public boolean isHorizonal() {
        return source.getY()==target.getY();
    }

    public boolean isVertical() {
        return source.getX()==target.getX();
    }

    public boolean isStrait() {
        return isHorizonal()||isVertical();
    }

    public Direction getDirection() {
        int dx = 0;
        int dy = 0;
        if (source.getX() < target.getX()) dx = 1;
        else if (source.getX() > target.getX()) dx = -1;
        if (source.getY() < target.getY()) dy = 1;
        else if (source.getY() > target.getY()) dy = -1;
        return Direction.getByDeltas(dx, dy);
    }

    public int getLength() {
        switch(getDirection()) {
            case SOUTH:
            case SW:
            case SE:
            case NORTH:
            case NE:
            case NW:
                return Math.abs(target.getY()-source.getY());
            case EST:case WEST:
                return Math.abs(target.getX()-source.getX());
            case NONE: return 0;
        }
        throw new IllegalArgumentException("direction not found");
    }


    static class LineFactory {
        private LineFactory() {
            super();
        }
        static Line createFromDef(String line) {
            String[] parts = line.split(" ");
            PointInt source = PointInt.PointIntFactory.createFromString(parts[0]);
            PointInt target = PointInt.PointIntFactory.createFromString(parts[2]);
            return new Line(source, target);
        }
    }

    static class LineBuilder {
        PointInt source;
        private LineBuilder(int x, int y) {
            this.source=PointInt.PointIntFactory.createFromCoords(x,y);
        }
        public static LineBuilder from(int  x, int y) {
            return new LineBuilder(x,y);
        }
        public Line to(int x,int y) {
            return new Line(this.source, PointInt.PointIntFactory.createFromCoords(x,y));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Line)) return false;
        Line line = (Line) o;
        return source.equals(line.source) && target.equals(line.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target);
    }

    @Override
    public String toString() {
        return "Line{" +
                "source=" + source +
                ", target=" + target +
                '}';
    }
}
