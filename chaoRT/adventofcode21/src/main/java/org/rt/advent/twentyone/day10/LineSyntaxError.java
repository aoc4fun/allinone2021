package org.rt.advent.twentyone.day10;

import java.text.ParseException;

public class LineSyntaxError extends ParseException {
    ChunkChars expected;
    ChunkChars found;

    public LineSyntaxError(ChunkChars expected, ChunkChars found, int errorOffset) {
        super("Error during line parse", errorOffset);
        this.expected = expected;
        this.found = found;
    }

    public ChunkChars getExpected() {
        return expected;
    }

    public ChunkChars getFound() {
        return found;
    }
}
