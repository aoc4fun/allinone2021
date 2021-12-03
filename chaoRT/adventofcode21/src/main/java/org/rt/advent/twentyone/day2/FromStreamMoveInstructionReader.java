package org.rt.advent.twentyone.day2;

import org.rt.advent.twentyone.io.BufferedReaderFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class FromStreamMoveInstructionReader implements MoveReader {
    private final BufferedReaderFactory factory;

    public FromStreamMoveInstructionReader(BufferedReaderFactory factory) {
        this.factory = factory;
    }

    @Override
    public Collection<MoveInstruction> read() {
        ArrayList<MoveInstruction> result = new ArrayList<>();

        try (BufferedReader reader = factory.openReader()) {
            String line = reader.readLine();
            while (line != null) {
                result.add(MoveInstructionFactory.createFromString(line));
                line = reader.readLine();
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
