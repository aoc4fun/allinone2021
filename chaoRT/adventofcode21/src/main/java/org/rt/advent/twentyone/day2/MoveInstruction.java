package org.rt.advent.twentyone.day2;

public class MoveInstruction {
    private int steps;
    private Direction direction;

    public MoveInstruction(int steps, Direction direction) {
        this.steps = steps;
        this.direction = direction;
    }

    public int getSteps() {
        return this.steps;
    }
    public Direction getDirection() {
        return this.direction;
    }
}
