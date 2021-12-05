package org.rt.advent.twentyone.day5;

enum Direction {
    NORTH(0, -1),
    EST(1, 0),
    SOUTH(0, 1),
    WEST(-1, 0),
    NW(-1, -1),
    SW(-1, 1),
    SE(1, 1),
    NE(1, -1),
    NONE(0, 0);

    int dx;
    int dy;

    private static Direction[][] byDeltas = new Direction[3][3];

    static {
        for (Direction d : values())
            byDeltas[d.getDx() + 1][d.getDy() + 1] = d;
    }

    static Direction getByDeltas(int dx, int dy) {
        checkInRange(dx, "dx");
        checkInRange(dy, "dy");
        return byDeltas[dx + 1][dy + 1];
    }

    static void checkInRange(int delta, String deltaName) {
        if (delta < -1 || delta > 1)
            throw new IllegalArgumentException("not a valid delta: " + delta + " for " + deltaName);
    }

    private Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

}
