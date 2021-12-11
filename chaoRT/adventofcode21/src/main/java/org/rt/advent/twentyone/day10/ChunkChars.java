package org.rt.advent.twentyone.day10;

import java.util.Optional;

public enum ChunkChars {
    BRACKET('(', ')', 3,1),
    SQUARE('[', ']',57,2),
    CURLY('{','}', 1197,3),
    DIAMOND('<','>', 25137, 4);
    private final char close;
    private final char open;
    long score;
    long nonCompletionScore;

    ChunkChars(char open, char close, long score, long nonCompletionScore) {
        this.open=open;
        this.score = score;
        this.close = close;
        this.nonCompletionScore = nonCompletionScore;
    }

    public char getClose() {
        return close;
    }

    public char getOpen() {
        return open;
    }

    public long getScore() {
        return score;
    }

    public long getNonCompletionScore() {
        return nonCompletionScore;
    }

    public static Optional<ChunkChars> getMatchingOpen(char c) {

        for(ChunkChars possible:ChunkChars.values()) {
            if(possible.getOpen()==c) {
                return Optional.of(possible);
            }
        }

        return Optional.empty();

    }

    public static Optional<ChunkChars> getMatchingOpenOrClose(char c) {

        for(ChunkChars possible:ChunkChars.values()) {
            if(possible.getOpen()==c || possible.getClose()==c) {
                return Optional.of(possible);
            }
        }

        return Optional.empty();

    }


    public static Optional<ChunkChars> getMatchingClose(char c) {

        for(ChunkChars possible:ChunkChars.values()) {
            if(possible.getClose()==c) {
                return Optional.of(possible);
            }
        }

        return Optional.empty();
    }

}
