package org.rt.advent.twentyone.day4;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public class BingoGameFactory {
    static BingoGame createFromReader(BufferedReader reader) throws IOException {
        BingoGame result = new BingoGame();
        result.draws=readDraws(reader);
        readBoards(reader, result);
        return result;
    }

    private static int[] readDraws(BufferedReader reader) throws IOException {
        return Arrays
                .stream(reader.readLine().split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
    private static void readBoards(BufferedReader reader, BingoGame result) throws IOException {

        while (reader.readLine()!=null) {
            result.boards.add(Board.BoardFactory.createFromBuffer(reader));
        }
    }
}
