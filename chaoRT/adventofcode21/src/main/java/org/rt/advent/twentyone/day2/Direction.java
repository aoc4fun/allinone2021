package org.rt.advent.twentyone.day2;

public enum Direction {
    FORWARD,
    UP,
    DOWN;

    public static Direction fromName(String name) {
        return valueOf(name.toUpperCase());
    }
}
