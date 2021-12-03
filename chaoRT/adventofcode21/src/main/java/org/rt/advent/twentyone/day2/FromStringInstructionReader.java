package org.rt.advent.twentyone.day2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class FromStringInstructionReader implements MoveReader {
    private final String[] instructions;

    public FromStringInstructionReader(String[] instructions) {
        this.instructions=instructions;
    }

    @Override
    public Collection<MoveInstruction> read() {
        return Arrays.stream(instructions)
                .map(s -> MoveInstructionFactory.createFromString(s))
                .collect(Collectors.toCollection(() -> new ArrayList<MoveInstruction>()));
    }
}
