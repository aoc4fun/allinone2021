package org.rt.advent.twentyone.day2;

import java.io.IOException;
import java.util.Collection;

public interface MoveReader {
    Collection<MoveInstruction> read();
}
