package org.rt.advent.twentyone.day2;

public class MoveInstructionFactory {

    public static MoveInstruction createFromString(String s) {
        String[] details = s.split(" ");
        return new MoveInstruction(Integer.parseInt(details[1]), Direction.fromName(details[0]));
    }
}
